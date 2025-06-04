package com.ssafy.sushi.global.sse;

import com.ssafy.sushi.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SseLikeCountEvent {

    private Integer totalLikes;

    public static SseLikeCountEvent of(User user) {
        return SseLikeCountEvent.builder()
                .totalLikes(user.getTotalLikes())
                .build();
    }

    public static SseLikeCountEvent of(Integer totalLikes) {
        return SseLikeCountEvent.builder()
                .totalLikes(totalLikes)
                .build();
    }
}
