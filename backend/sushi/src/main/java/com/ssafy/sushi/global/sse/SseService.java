package com.ssafy.sushi.global.sse;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();
//    private static final Long TIMEOUT = 60L * 1000 * 60; // 60분
    private static final Long TIMEOUT = 60L * 1000 * 3; // 3분

    public SseEmitter subscribe(Integer userId) {
        SseEmitter emitter = emitters.computeIfAbsent(userId, key -> {
            SseEmitter newEmitter = new SseEmitter(TIMEOUT);

            newEmitter.onCompletion(() -> {
                emitters.remove(userId);
            });

            newEmitter.onTimeout(() -> {
                emitters.remove(userId);
                newEmitter.complete();
            });

            newEmitter.onError((e) -> {
                emitters.remove(userId);
                newEmitter.complete();
            });

            return newEmitter;
        });

        try {
            emitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (Exception e) {
//            log.warn("Failed to send initial event to user {}", userId);
            emitters.remove(userId);
        }

        return emitter;
    }

    public void sendNotification(Integer userId, String eventType, Object eventData) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event()
                        .name(eventType)  // "notification" 또는 "likeCount"
                        .data(eventData));
            } catch (Exception e) {
//                log.error("Error sending {} event to user {}: {}", eventType, userId, e.getMessage());
                log.warn("Failed to send {} event to user {}", eventType, userId);
                removeEmitterSafely(userId, emitter);
            }
        }
    }

    private void removeEmitterSafely(Integer userId, SseEmitter emitter) {
        try {
            if (emitter != null) {
                emitter.complete();
            }
        } catch (Exception e) {
//            log.debug("Error during safe emitter removal for user {}: {}", userId, e.getMessage());
        } finally {
            emitters.remove(userId);
        }
    }

    // 기존 notify 메소드들은 sendNotification을 호출하도록 변경
    public void notify(Integer userId, SseNotificationEvent event) {
        sendNotification(userId, "notification", event);
    }

    public void notifyLikeCount(Integer userId, SseLikeCountEvent event) {
        sendNotification(userId, "likeCount", event);
    }

    @PreDestroy
    public void destroy() {
        log.info("Closing all SSE connections");
        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event()
                        .name("shutdown")
                        .data("Server shutting down"));
                Thread.sleep(100);
                emitter.complete();
            } catch (Exception e) {
//                log.error("Error during emitter shutdown for user {}", userId, e);
                log.warn("Error during emitter shutdown for user {}", userId);
            }
        });
        emitters.clear();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("All SSE connections have been closed");
    }

    @Scheduled(fixedRate = 60000)  // 1분마다 체크
    public void cleanupStaleConnections() {
//        log.info("Checking for stale SSE connections...");
        Set<Integer> toRemove = new HashSet<>();

        emitters.forEach((userId, emitter) -> {
            try {
                emitter.send(SseEmitter.event().comment(""));
            } catch (Exception e) {
                toRemove.add(userId);
            }
        });

        toRemove.forEach(userId -> {
            SseEmitter emitter = emitters.remove(userId);
            if (emitter != null) {
                try {
                    emitter.complete();
//                    log.info("complete one and now : {}", emitters.size());
                } catch (Exception e) {
//                    log.debug("this error {}", e.getMessage());
//                    log.info("Emitter already completed for user: {}", userId);
                }
            }
        });

        if (!toRemove.isEmpty()) {
            log.debug("Cleaned up {} stale connections. Current active: {}",
                    toRemove.size(), emitters.size());
        }
    }
}