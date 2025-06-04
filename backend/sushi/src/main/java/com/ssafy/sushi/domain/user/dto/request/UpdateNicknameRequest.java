package com.ssafy.sushi.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateNicknameRequest {

    @NotBlank
    private String nickname;
}
