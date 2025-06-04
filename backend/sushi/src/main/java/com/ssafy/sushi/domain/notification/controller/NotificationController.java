package com.ssafy.sushi.domain.notification.controller;

import com.ssafy.sushi.domain.notification.dto.response.HasUnreadNotificationResponse;
import com.ssafy.sushi.domain.notification.dto.response.MyNotificationListResponse;
import com.ssafy.sushi.domain.notification.service.NotificationService;
import com.ssafy.sushi.global.common.CustomPage;
import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.security.UserPrincipal;
import com.ssafy.sushi.global.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthenticationUtil authenticationUtil;
    private final SseService sseService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<CustomPage<MyNotificationListResponse>>> getMyNotificationList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(notificationService.getMyNotificationList(userId, pageable));
    }

    @GetMapping("/unread-exists")
    public ResponseEntity<ApiResponse<HasUnreadNotificationResponse>> hasUnreadNotification(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(notificationService.hasUnreadNotification(userId));
    }

    @PutMapping("/{notificationId}")
    public ResponseEntity<ApiResponse<Void>> markNotificationAsRead(
            @PathVariable("notificationId") Integer notificationId) {
        notificationService.markNotificationAsRead(notificationId);

        return ApiResponse.success(HttpStatus.OK);
    }

    @PutMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> markAllNotificationAsRead(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);
        notificationService.markAllNotificationAsRead(userId);

        return ApiResponse.success(HttpStatus.OK);
    }
}
