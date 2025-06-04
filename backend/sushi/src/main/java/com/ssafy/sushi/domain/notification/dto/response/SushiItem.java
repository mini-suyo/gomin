package com.ssafy.sushi.domain.notification.dto.response;

import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SushiItem {
    private String title;
    private Integer sushiType;
    private Integer category;

    public static SushiItem of(Sushi sushi) {
        return SushiItem.builder()
                .title(sushi.getTitle())
                .sushiType(sushi.getSushiType().getId())
                .category(sushi.getCategory().getId())
                .build();
    }
}
