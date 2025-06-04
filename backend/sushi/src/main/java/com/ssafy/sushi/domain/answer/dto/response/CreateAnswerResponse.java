package com.ssafy.sushi.domain.answer.dto.response;

import com.ssafy.sushi.domain.answer.entity.Answer;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateAnswerResponse {

    private Integer answerId;
    private Integer userId;
    private Integer sushiId;
    private String content;

    public static CreateAnswerResponse of(Answer answer){
        return CreateAnswerResponse.builder()
                .answerId(answer.getId())
                .userId(answer.getUser().getId())
                .sushiId(answer.getSushi().getId())
                .content(answer.getContent())
                .build();
    }

}
