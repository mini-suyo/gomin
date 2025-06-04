package com.ssafy.sushi.domain.user.controller;

import com.ssafy.sushi.domain.auth.dto.TokenValidationResult;
import com.ssafy.sushi.domain.user.dto.request.UpdateNicknameRequest;
import com.ssafy.sushi.domain.user.dto.response.UserLikeNumResponse;
import com.ssafy.sushi.domain.user.dto.response.UserNicknameChangeResponse;
import com.ssafy.sushi.domain.user.service.UserService;
import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.security.UserPrincipal;
import com.ssafy.sushi.global.security.jwt.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationUtil authenticationUtil;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/validate/{token}")
    public ResponseEntity<ApiResponse<Boolean>> validateToken(@PathVariable String token) {
        TokenValidationResult result = jwtTokenProvider.validateToken(token);
        if (!result.isValid()) {
            return ApiResponse.success(false);
        }

        try {
            Integer userId = jwtTokenProvider.getUserId(token);
            Boolean exists = userService.existsById(userId);
            return ApiResponse.success(exists);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ApiResponse.success(false);
        }
    }

    @DeleteMapping("/me")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        userService.deleteUser(userId);

        return ApiResponse.success(HttpStatus.OK);
    }

    @GetMapping("/my-like")
    public ResponseEntity<ApiResponse<UserLikeNumResponse>> getUserLikeNum(
            @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(userService.getUserLikeNum(userId));
    }

    @PutMapping("/nickname")
    public ResponseEntity<ApiResponse<UserNicknameChangeResponse>> updateNickname(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody @Valid UpdateNicknameRequest request) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(userService.updateNickname(userId, request));
    }
}
