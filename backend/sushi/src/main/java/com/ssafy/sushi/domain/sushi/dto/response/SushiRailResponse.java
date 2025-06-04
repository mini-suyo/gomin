package com.ssafy.sushi.domain.sushi.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class SushiRailResponse {
    List<SushiRailItem> sushi;
}
