package com.ssafy.sushi.domain.sushi.dto.response;

import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateSushiResponse {

    private Integer sushiId;
    private String title;
    private String content;
    private Integer category;
    private Integer sushiType;
    private LocalDateTime createdAt;
    private LocalDateTime expirationTime;
    private Integer maxAnswers;
    private Integer remainingAnswers;
    private String token;

    public static CreateSushiResponse of(Sushi sushi, String token) {
        return CreateSushiResponse.builder()
                .sushiId(sushi.getId())
                .title(sushi.getTitle())
                .content(sushi.getContent())
                .category(sushi.getCategory().getId())
                .sushiType(sushi.getSushiType().getId())
                .createdAt(sushi.getCreatedAt())
                .expirationTime(sushi.getExpirationTime())
                .maxAnswers(sushi.getMaxAnswers())
                .remainingAnswers(sushi.getRemainingAnswers())
                .token(token)
                .build();
    }
}
