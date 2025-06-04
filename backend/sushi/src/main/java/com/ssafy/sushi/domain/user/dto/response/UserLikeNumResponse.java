package com.ssafy.sushi.domain.user.dto.response;

import com.ssafy.sushi.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLikeNumResponse {
    private Integer totalLikes;

    public static UserLikeNumResponse of(User user) {
        return UserLikeNumResponse.builder()
                .totalLikes(user.getTotalLikes())
                .build();
    }

    public static UserLikeNumResponse of(Integer totalLikes) {
        return UserLikeNumResponse.builder()
                .totalLikes(totalLikes)
                .build();
    }
}
