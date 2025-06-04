package com.ssafy.sushi.domain.notification.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum NotificationType {
    EXP('1', "초밥 유통기한이 마감됐다냥.", "/sushidetail/"), // 유통기한 마감
    ANS_END('2', "답변 마감이 완료됐다냥.", "/sushidetail/"), // 답변 마감
    ANS_LIKE('3', "답변에 좋아요가 눌렸다냥!", "/sushianswerdetail/"); // 답변 좋아요

    private final char code;
    private final String message;
    private final String redirectUrl;

    NotificationType(char code, String message, String redirectUrl) {
        this.code = code;
        this.message = message;
        this.redirectUrl = redirectUrl;
    }

    public static NotificationType fromCode(Character code) {
        return Arrays.stream(NotificationType.values())
                .filter(type -> type.code == code)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid NotificationType code: " + code));
    }

}