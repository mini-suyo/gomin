package com.ssafy.sushi.domain.notification.dto.response;

import com.ssafy.sushi.domain.notification.entity.Notification;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MyNotificationListResponse {
    private Integer notificationId;
    private Integer notificationType;
    private String message;
    private String redirectUrl;
    private LocalDateTime createdAt;
    private SushiItem sushi;

    public static MyNotificationListResponse of(Notification notification){
        return MyNotificationListResponse.builder()
                .notificationId(notification.getId())
                .notificationType(Character.getNumericValue(notification.getNotificationType().getCode()))
                .message(notification.getMessage())
                .redirectUrl(notification.getRedirectUrl())
                .createdAt(notification.getCreatedAt())
                .sushi(SushiItem.of(notification.getSushi()))
                .build();
    }
}
