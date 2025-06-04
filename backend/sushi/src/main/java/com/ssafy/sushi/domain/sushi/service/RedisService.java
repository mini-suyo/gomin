package com.ssafy.sushi.domain.sushi.service;

import com.ssafy.sushi.domain.sushi.dto.ScheduleTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    private final StringRedisTemplate redisTemplate;

    public void registerSchedule(Integer sushiId, Instant expirationTime) {
        String key = getRedisKey(sushiId);
        redisTemplate.opsForValue().set(key, String.valueOf(expirationTime.getEpochSecond()));
    }

    public void cancelSchedule(Integer sushiId) {
        redisTemplate.delete(getRedisKey(sushiId));
    }

    public boolean hasSchedule(Integer sushiId) {
        return redisTemplate.hasKey(getRedisKey(sushiId));
    }

    public List<ScheduleTask> findAllPendingTasks() {
        Set<String> keys = redisTemplate.keys("sushi:schedule:*");
        if (keys.isEmpty()) {
            return Collections.emptyList();
        }

        List<ScheduleTask> tasks = new ArrayList<>();

        for (String key : keys) {
            String expiredAtStr = redisTemplate.opsForValue().get(key);
            if (expiredAtStr == null) continue;

            Integer sushiId = extractSushiId(key);
            Instant expiredAt = Instant.ofEpochSecond(Long.parseLong(expiredAtStr));

            tasks.add(new ScheduleTask(sushiId, expiredAt));
        }

        return tasks;
    }

    private String getRedisKey(Integer sushiId) {
        return "sushi:schedule:" + sushiId;
    }

    private Integer extractSushiId(String key) {
        return Integer.parseInt(key.split(":")[2]);
    }
}