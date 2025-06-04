package com.ssafy.sushi.domain.notification.service;


import com.ssafy.sushi.domain.notification.dto.response.HasUnreadNotificationResponse;
import com.ssafy.sushi.domain.notification.dto.response.MyNotificationListResponse;
import com.ssafy.sushi.domain.notification.entity.Notification;
import com.ssafy.sushi.domain.notification.enums.NotificationType;
import com.ssafy.sushi.domain.notification.repository.NotificationRepository;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.global.common.CustomPage;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import com.ssafy.sushi.global.infra.fcm.FcmRepository;
import com.ssafy.sushi.global.infra.fcm.FcmService;
import com.ssafy.sushi.global.infra.fcm.FcmToken;
import com.ssafy.sushi.global.infra.fcm.dto.LikeFcmRequestDto;
import com.ssafy.sushi.global.infra.fcm.dto.SoldOutFcmRequestDto;
import com.ssafy.sushi.global.sse.SseNotificationEvent;
import com.ssafy.sushi.global.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final SseService sseService;
    private final FcmService fcmService;
    private final NotificationRepository notificationRepository;
    private final FcmRepository fcmRepository;

    @Value("${app.domain}")
    private String host;

    @Transactional
    public void sendNotification(User receiveUser, Sushi sushi, NotificationType notificationType, Integer parameter) {

        Notification notification = Notification.builder()
                .user(receiveUser)
                .sushi(sushi)
                .notificationType(notificationType)
                .message(notificationType.getMessage())
                .redirectUrl(host + notificationType.getRedirectUrl() + parameter.toString())
                .build();

        notificationRepository.save(notification);
        SseNotificationEvent event = SseNotificationEvent.of(true);

        if (notificationType.getCode() == '1' || notificationType.getCode() == '2') {
            List<FcmToken> tokens = fcmRepository.findAllByUserId(receiveUser.getId());
            for (FcmToken fcmToken : tokens) {
                try {
                    fcmService.sendSoldOutFCM(SoldOutFcmRequestDto.of(
                            fcmToken.getToken(),
                            receiveUser.getNickname(),
                            sushi.getTitle()
                    ));
                } catch (Exception e) {
                    log.error("Failed to send FCM notification to token: {}", fcmToken.getToken(), e);
                }
            }
        } else if (notificationType.getCode() == '3') {
            List<FcmToken> tokens = fcmRepository.findAllByUserId(receiveUser.getId());
            for (FcmToken fcmToken : tokens) {
                try {
                    fcmService.sendLikeFCM(LikeFcmRequestDto.of(
                            fcmToken.getToken(),
                            receiveUser.getNickname(),
                            sushi.getTitle()
                    ));
                } catch (Exception e) {
                    log.error("Failed to send FCM notification to token: {}", fcmToken.getToken(), e);
                }
            }
        }

        sseService.notify(receiveUser.getId(), event);
    }

    public CustomPage<MyNotificationListResponse> getMyNotificationList(Integer userId, Pageable pageable) {
        Page<Notification> notificationList = notificationRepository.findNotificationByUserIdAndIsReadFalse(userId, pageable);

        return new CustomPage<>(notificationList.map(MyNotificationListResponse::of));
    }

    public HasUnreadNotificationResponse hasUnreadNotification(Integer userId) {
        boolean hasUnread = notificationRepository.existsByUserIdAndIsReadFalse(userId);

        return HasUnreadNotificationResponse.builder()
                .hasUnread(hasUnread)
                .build();
    }

    @Transactional
    public void markNotificationAsRead(Integer notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOTIFICATION_NOT_FOUND));

        if (!notification.getIsRead()) {
            notification.markNotificationAsRead();
            notificationRepository.save(notification);
        }
    }

    @Transactional
    public void markAllNotificationAsRead(Integer userId) {
        notificationRepository.markAllNotificationAsRead(userId);
    }
}
