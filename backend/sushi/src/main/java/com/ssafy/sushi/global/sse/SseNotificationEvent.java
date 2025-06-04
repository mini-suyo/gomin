package com.ssafy.sushi.global.sse;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SseNotificationEvent {
    private boolean hasUnread;

    public static SseNotificationEvent of(boolean hasUnread) {
        return SseNotificationEvent.builder()
                .hasUnread(hasUnread)
                .build();
    }
}
