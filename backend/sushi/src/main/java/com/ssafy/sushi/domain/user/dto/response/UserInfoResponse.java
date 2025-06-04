package com.ssafy.sushi.domain.user.dto.response;

import com.ssafy.sushi.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserInfoResponse {
    private String id;
    private String nickname;
    private int totalLikes;


    public UserInfoResponse(String id, String nickname, int totalLikes) {
        this.id = id;
        this.nickname = nickname;
        this.totalLikes = totalLikes;
    }

    public static UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .id(user.getId().toString())
                .nickname(user.getNickname())
                .totalLikes(user.getTotalLikes())
                .build();
    }
}
