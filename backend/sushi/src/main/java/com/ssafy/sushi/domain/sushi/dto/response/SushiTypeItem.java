package com.ssafy.sushi.domain.sushi.dto.response;

import com.ssafy.sushi.domain.sushi.entity.SushiType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SushiTypeItem {
    private Integer id;
    private String name;
    private Integer requiredLikes;

    public static SushiTypeItem of(SushiType sushiType) {
        return SushiTypeItem.builder()
                .id(sushiType.getId())
                .name(sushiType.getName())
                .requiredLikes(sushiType.getRequiredLikes())
                .build();
    }
}
