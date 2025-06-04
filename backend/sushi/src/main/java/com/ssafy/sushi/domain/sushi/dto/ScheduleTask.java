package com.ssafy.sushi.domain.sushi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor
public class ScheduleTask {
    private Integer sushiId;
    private Instant expirationTime;
}
