package com.ssafy.sushi.domain.user.dto.response;

import com.ssafy.sushi.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserNicknameChangeResponse {
    private Integer id;
    private String nickname;
    private String provider;
    private LocalDateTime updated_at;


    public static UserNicknameChangeResponse of(User user) {
        return UserNicknameChangeResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .provider(user.getProvider().getName())
                .updated_at(user.getUpdatedAt())
                .build();
    }
}
