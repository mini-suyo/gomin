package com.ssafy.sushi.domain.sushi.service;

import com.ssafy.sushi.domain.answer.service.AnswerService;
import com.ssafy.sushi.domain.notification.enums.NotificationType;
import com.ssafy.sushi.domain.notification.service.NotificationService;
import com.ssafy.sushi.domain.sushi.dto.ScheduleTask;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.sushi.repository.SushiRepository;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import com.ssafy.sushi.global.infra.chatgpt.GPTService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Service
public class ScheduleService {
    private final SushiRepository sushiRepository;
    private final NotificationService notificationService;
    private final AnswerService answerService;
    private final GPTService gptService;
    private final RedisService redisService;

    public void sushiEnd(Sushi sushi) {
        // LocalDateTime -> Instant 변환
        Instant expirationInstant = sushi.getExpirationTime()
                .atZone(ZoneId.systemDefault())
                .toInstant();

        // Redis에 스케줄 등록
        redisService.registerSchedule(sushi.getId(), expirationInstant);

        // 스케줄링 작업 등록
        Duration remainingTime = Duration.between(Instant.now(), expirationInstant);
        scheduleEndTask(sushi.getId(), remainingTime);
    }

    @PostConstruct
    public void initializeScheduledTasks() {
        List<ScheduleTask> tasks = redisService.findAllPendingTasks();
        Instant now = Instant.now();

        for (ScheduleTask task : tasks) {
            if (now.isAfter(task.getExpirationTime())) {  // 현재시간 > 유통기한
                try {
                    executeSushiEndTask(task.getSushiId());
                } catch (CustomException e) {
                    log.error("Failed to execute sushi end task during initialization. SushiId: {}, Error: {}",
                            task.getSushiId(), e.getMessage());
                } finally {
                    redisService.cancelSchedule(task.getSushiId());
                }
            } else { // 현재시간 < 유통기한
                Duration remainingTime = Duration.between(
                        now,
                        task.getExpirationTime()
                );
                scheduleEndTask(task.getSushiId(), remainingTime);
            }
        }
    }

    public void scheduleEndTask(Integer sushiId, Duration delay) {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.schedule(() -> {
            if (redisService.hasSchedule(sushiId)) {
                try {
                    executeSushiEndTask(sushiId);
                } catch (Exception e) {
                    log.error("Failed to execute scheduled task. SushiId: {}, Error: {}",
                            sushiId, e.getMessage());
                } finally {
                    redisService.cancelSchedule(sushiId);
                }
            }
        }, delay.toSeconds(), TimeUnit.SECONDS);
    }

    private void executeSushiEndTask(Integer sushiId) {
        Sushi freshSushi = sushiRepository.findById(sushiId).orElseThrow(() ->
                new CustomException(ErrorCode.SUSHI_NOT_FOUND));


        if (!freshSushi.getIsClosed()) {
            if (freshSushi.canAnswer()) {
                try {
                    String gptAnswer = gptService.generateGPTAnswer(
                            freshSushi.getTitle(),
                            freshSushi.getContent()
                    );
                    answerService.saveGPTAnswer(freshSushi, gptAnswer);
                    freshSushi.reduceRemainingAnswers();
                } catch (CustomException e) {
                    log.error("GPT API 호출 실패: {}", e.getMessage());
                }
            }

            freshSushi.closeSushi();
            sushiRepository.save(freshSushi);


            notificationService.sendNotification(
                    freshSushi.getUser(),
                    freshSushi,
                    NotificationType.EXP,
                    freshSushi.getId()
            );

            log.info("초밥이 마감 처리되었습니다: Id = {}", freshSushi.getId());
        } else {
            log.info("초밥은 이미 마감되었습니다: Id = {}", sushiId);
        }
    }
}
