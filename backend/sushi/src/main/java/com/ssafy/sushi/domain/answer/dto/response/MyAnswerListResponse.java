package com.ssafy.sushi.domain.answer.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyAnswerListResponse {
    private Integer sushiId;
    private Integer category;
    private Integer sushiType;
    private String title;
    private String content;
    private Boolean isLiked;
    private LocalDateTime createdAt;

}
