package com.ssafy.sushi.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", "잘못된 입력값입니다"),
    RESOURCE_NOT_FOUND(404, "C002", "요청한 리소스를 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(500, "C003", "서버 내부 오류가 발생했습니다"),


    // Authorization
    UNAUTHORIZED_ACCESS(401, "A001", "로그인이 필요한 서비스입니다"),
    FORBIDDEN_ACCESS(403, "A002", "접근 권한이 없습니다"),

    // OAUTH
    OAUTH_SERVER_ERROR(500, "O001", "OAuth 서버 오류가 발생했습니다"),
    INVALID_OAUTH_PROVIDER(400, "O002", "지원하지 않는 OAuth 제공자입니다"),
    INVALID_REFRESH_TOKEN(401, "O003", "Invalid refresh token"),
    REFRESH_TOKEN_NOT_FOUND(401, "O004", "Refresh token not found"),
    REFRESH_TOKEN_MISMATCH(401, "O005", "Refresh token mismatch"),

    // USER
    USER_NOT_FOUND(404, "U001", "유저를 찾을 수 없습니다."),

    // SUSHI
    CATEGORY_NOT_FOUND(404, "S001", "존재하지 않는 카테고리입니다."),
    SUSHITYPE_NOT_FOUND(404, "S002", "존재하지 않는 초밥유형입니다."),
    SUSHI_NOT_FOUND(404, "S003", "존재하지 않는 초밥입니다."),
    SUSHI_IS_CLOSED(500, "S004", "이미 마감된 초밥입니다."),
    UNAUTHORIZED_SUSHI_ACCESS(403, "S005", "해당 초밥에 대한 권한이 없습니다."),

    // ANSWER
    ANSWER_IS_FULL(500, "R001", "답변 가능 인원이 초과되었습니다."),
    ANSWER_NOT_FOUND(404, "R002", "답변을 찾을 수 없습니다."),
    ANSWER_ALREADY_LIKED(400, "R003", "이미 좋아요를 누른 답변입니다."),
    SELF_LIKE_DENIED(400, "R004", "자신의 답변에는 좋아요를 누를 수 없습니다."),
    SELF_ANSWER_DENIED(400, "R005", "자신의 초밥에는 답변할 수 없습니다."),
    ALREADY_ANSWERED(400, "R006", "이미 답변한 초밥입니다."),

    // NOTIFICATION
    NOTIFICATION_NOT_FOUND(404, "N001", "알림을 찾을 수 없습니다."),

    // TOKEN
    TOKEN_NOT_FOUND(404, "T001", "토큰을 찾을 수 없습니다."),

    // GPT
    GPT_API_ERROR(400, "G001", "GPT API 호출에 실패했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
