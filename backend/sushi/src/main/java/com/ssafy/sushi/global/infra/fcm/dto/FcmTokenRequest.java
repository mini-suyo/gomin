package com.ssafy.sushi.global.infra.fcm.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FcmTokenRequest {
    private String token;
}
