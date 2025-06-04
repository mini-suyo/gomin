package com.ssafy.sushi.domain.sushi.dto.response;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SushiRailItem {
    private Integer sushiId;
    private Integer category;
    private Integer sushiType;
    private Integer remainingAnswers;
    private LocalDateTime expirationTime;
}
