package com.ssafy.sushi.domain.notification.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HasUnreadNotificationResponse {
    private boolean hasUnread;
}
