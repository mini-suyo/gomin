package com.ssafy.sushi.global.infra.fcm;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.domain.user.repository.UserRepository;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import com.ssafy.sushi.global.infra.fcm.dto.LikeFcmRequestDto;
import com.ssafy.sushi.global.infra.fcm.dto.SoldOutFcmRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FcmService {

    private final UserRepository userRepository;
    private final FcmRepository fcmRepository;
    private final FirebaseMessaging firebaseMessaging;

    @Transactional
    public void saveToken(Integer userId, String token) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 동일한 토큰이 이미 있는지 확인
        if (!fcmRepository.existsByToken(token)) {
            FcmToken fcmToken = FcmToken.create(user, token);
            fcmRepository.save(fcmToken);
        }
    }

    // 토큰 삭제
    @Transactional
    public void removeToken(String token) {
        fcmRepository.deleteByToken(token);
    }

    public void sendSoldOutFCM(SoldOutFcmRequestDto soldOutFcmRequestDto) {
        Message message = createFCMMessage(
                soldOutFcmRequestDto.getFcm(),
                "고민 한 접시",
                soldOutFcmRequestDto.getNickname()+"님! '" + soldOutFcmRequestDto.getReviewTitle() + "' 초밥이 마감되었습니다!"
        );

        try {
            sendMessage(message);
        } catch (RuntimeException e) {
            removeToken(soldOutFcmRequestDto.getFcm());
        }
    }

    public void sendLikeFCM(LikeFcmRequestDto likeFcmRequestDto) {
        Message message = createFCMMessage(
                likeFcmRequestDto.getFcm(),
                "고민 한 접시",
                likeFcmRequestDto.getNickname() + "님! '" + likeFcmRequestDto.getReviewTitle() + "'에 대한 답글에 좋아요를 받았습니다!"
        );

        try {
            sendMessage(message);
        } catch (RuntimeException e) {
            removeToken(likeFcmRequestDto.getFcm());
        }
    }

    private Message createFCMMessage(String fcmToken, String title, String body) {
        return Message.builder()
                .setToken(fcmToken)
                .putData("title", title)
                .putData("body", body)
                .putData("url", "https://www.gomin.my/")
                .putData("timestamp", String.valueOf(System.currentTimeMillis()))
                .build();
    }

    private void sendMessage(Message message) {
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            String errorResponse = createErrorResponse("FCM_ERROR", "FCM 메시지 전송 실패: " + e.getMessage());
            throw new RuntimeException(errorResponse);
        } catch (Exception e) {
            String errorResponse = createErrorResponse("UNKNOWN_ERROR", "FCM 메시지 전송 실패: " + e.getMessage());
            throw new RuntimeException(errorResponse);
        }
    }

    private String createErrorResponse(String errorCode, String message) {
        return String.format("{\"errorCode\": \"%s\", \"message\": \"%s\"}", errorCode, message);
    }
}
