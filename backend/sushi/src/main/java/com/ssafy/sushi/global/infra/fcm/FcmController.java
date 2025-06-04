package com.ssafy.sushi.global.infra.fcm;

import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.infra.fcm.dto.FcmTokenRequest;
import com.ssafy.sushi.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;
    private final AuthenticationUtil authenticationUtil;

    @PostMapping("/token")
    public ResponseEntity<ApiResponse<Void>> registerToken(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody FcmTokenRequest request) {

        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        fcmService.saveToken(userId, request.getToken());
        return ApiResponse.success(HttpStatus.CREATED);
    }

    @DeleteMapping("/token")
    public ResponseEntity<ApiResponse<Void>> removeToken(
            @RequestBody FcmTokenRequest request) {

        fcmService.removeToken(request.getToken());
        return ApiResponse.success(HttpStatus.OK);
    }
}
