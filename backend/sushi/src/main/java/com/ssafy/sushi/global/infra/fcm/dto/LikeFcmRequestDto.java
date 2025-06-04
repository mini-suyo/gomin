package com.ssafy.sushi.global.infra.fcm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class LikeFcmRequestDto {
    private String fcm;
    private String nickname;
    private String reviewTitle;

    public static LikeFcmRequestDto of(String token, String nickname, String title) {
        return LikeFcmRequestDto.builder()
                .fcm(token)
                .nickname(nickname)
                .reviewTitle(title)
                .build();
    }
}
