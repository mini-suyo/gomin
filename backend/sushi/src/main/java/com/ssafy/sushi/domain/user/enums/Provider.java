package com.ssafy.sushi.domain.user.enums;

public enum Provider {
    KAKAO, GOOGLE;

    public String getName() {
        return this.name().toLowerCase();
    }
}
