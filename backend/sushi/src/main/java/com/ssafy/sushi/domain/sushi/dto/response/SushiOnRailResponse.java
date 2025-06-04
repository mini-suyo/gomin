package com.ssafy.sushi.domain.sushi.dto.response;

import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class SushiOnRailResponse {
    private Integer sushiId;
    private String title;
    private String content;
    private Integer category;
    private Integer sushiType;
    private Integer maxAnswers;
    private Integer remainingAnswers;
    private LocalDateTime expirationTime;
    private Boolean isClosed;

    public static SushiOnRailResponse of(Sushi sushi) {
        return SushiOnRailResponse.builder()
                .sushiId(sushi.getId())
                .title(sushi.getTitle())
                .content(sushi.getContent())
                .category(sushi.getCategory().getId())
                .sushiType(sushi.getSushiType().getId())
                .maxAnswers(sushi.getMaxAnswers())
                .remainingAnswers(sushi.getRemainingAnswers())
                .expirationTime(sushi.getExpirationTime())
                .isClosed(sushi.getIsClosed())
                .build();
    }
}
