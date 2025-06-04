package com.ssafy.sushi.domain.notification.entity;

import com.ssafy.sushi.domain.notification.enums.NotificationType;
import com.ssafy.sushi.domain.notification.enums.NotificationTypeConverter;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.global.common.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sushi_id", nullable = true) // nullable = true가 기본값이라 생략 가능
    private Sushi sushi;

    @Convert(converter = NotificationTypeConverter.class)
    private NotificationType notificationType;

    @Column(nullable = false)
    private String message;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "redirect_url", nullable = false)
    private String redirectUrl;

    public void markNotificationAsRead(){
        this.isRead = true;
    }

}
