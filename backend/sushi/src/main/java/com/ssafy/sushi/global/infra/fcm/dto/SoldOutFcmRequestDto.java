package com.ssafy.sushi.global.infra.fcm.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class SoldOutFcmRequestDto {
    private String fcm;
    private String nickname;
    private String reviewTitle;

    public static SoldOutFcmRequestDto of(String token, String nickname, String title) {
        return SoldOutFcmRequestDto.builder()
                .fcm(token)
                .nickname(nickname)
                .reviewTitle(title)
                .build();
    }
}
