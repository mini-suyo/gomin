package com.ssafy.sushi.domain.sushi.dto.request;

import com.ssafy.sushi.domain.sushi.constants.SushiConstants;
import com.ssafy.sushi.domain.sushi.entity.Category;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.sushi.entity.SushiType;
import com.ssafy.sushi.domain.user.entity.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateSushiRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    @Min(1) @Max(10)
    private Integer maxAnswers;

    @NotNull
    @Min(1)
    private Integer category;

    @NotNull
    @Min(1)
    private Integer sushiType;

    public Sushi toEntity(CreateSushiRequest CreateSushiRequest, User user, Category category, SushiType sushiType) {

        return Sushi.builder()
                .user(user)
                .category(category)
                .sushiType(sushiType)
                .title(CreateSushiRequest.getTitle())
                .content(CreateSushiRequest.getContent())
                .expirationTime(LocalDateTime.now().plusMinutes(SushiConstants.EXPIRATION_MINUTES)) // 유통기한은 24시간 후
                .maxAnswers(CreateSushiRequest.getMaxAnswers())
                .remainingAnswers(CreateSushiRequest.getMaxAnswers())
                .build();
    }
}
