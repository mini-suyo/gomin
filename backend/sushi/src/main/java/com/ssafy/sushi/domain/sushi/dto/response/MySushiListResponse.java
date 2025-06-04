package com.ssafy.sushi.domain.sushi.dto.response;

import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MySushiListResponse {
    private Integer sushiId;
    private Integer category;
    private Integer sushiType;
    private String title;
    private String content;
    private Integer maxAnswers;
    private Integer remainingAnswers;
    private Boolean isClosed;
    private LocalDateTime expirationTime;
    private LocalDateTime createdAt;

    public static MySushiListResponse of(Sushi sushi) {
        return MySushiListResponse.builder()
                .sushiId(sushi.getId())
                .category(sushi.getCategory().getId())
                .sushiType(sushi.getSushiType().getId())
                .title(sushi.getTitle())
                .content(sushi.getContent())
                .maxAnswers(sushi.getMaxAnswers())
                .remainingAnswers(sushi.getRemainingAnswers())
                .isClosed(sushi.getIsClosed())
                .expirationTime(sushi.getExpirationTime())
                .createdAt(sushi.getCreatedAt())
                .build();
    }
}
