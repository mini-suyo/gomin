package com.ssafy.sushi.domain.answer.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateAnswerRequest {

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;

}

