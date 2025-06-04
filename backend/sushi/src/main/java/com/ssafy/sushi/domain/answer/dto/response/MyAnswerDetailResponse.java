package com.ssafy.sushi.domain.answer.dto.response;

import com.ssafy.sushi.domain.answer.entity.Answer;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyAnswerDetailResponse {

    private Integer sushiId;
    private String title;
    private String content;
    private Integer category;
    private Integer sushiType;
    private Integer maxAnswers;
    private Integer remainingAnswers;
    private Boolean isLiked;
    private LocalDateTime createdAt;
    private String answer;

    public static MyAnswerDetailResponse of(Answer answer) {
        Sushi sushi = answer.getSushi();

        return MyAnswerDetailResponse.builder()
                .sushiId(sushi.getId())
                .title(sushi.getTitle())
                .content(sushi.getContent())
                .category(sushi.getCategory().getId())
                .sushiType(sushi.getSushiType().getId())
                .maxAnswers(sushi.getMaxAnswers())
                .remainingAnswers(sushi.getRemainingAnswers())
                .createdAt(sushi.getCreatedAt())
                .isLiked(answer.getIsLiked())
                .answer(answer.getContent())
                .build();
    }
}
