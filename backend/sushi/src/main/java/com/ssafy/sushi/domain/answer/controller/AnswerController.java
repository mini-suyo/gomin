package com.ssafy.sushi.domain.answer.controller;

import com.ssafy.sushi.domain.answer.dto.response.MyAnswerDetailResponse;
import com.ssafy.sushi.domain.answer.dto.response.MyAnswerListResponse;
import com.ssafy.sushi.domain.answer.service.AnswerService;
import com.ssafy.sushi.global.common.CustomPage;
import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.security.UserPrincipal;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;
    private final AuthenticationUtil authenticationUtil;

    @GetMapping("")
    public ResponseEntity<ApiResponse<CustomPage<MyAnswerListResponse>>> getMyAnswerList(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) String keyword,
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(answerService.getMyAnswerList(userId, keyword, pageable));
    }

    @GetMapping("/{sushiId}")
    public ResponseEntity<ApiResponse<MyAnswerDetailResponse>> getMyAnswerDetail(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("sushiId") @Positive Integer sushiId) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        return ApiResponse.success(answerService.getMyAnswerDetail(userId, sushiId));
    }

    @PostMapping("/{answerId}/like")
    public ResponseEntity<ApiResponse<Void>> likeAnswer(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("answerId") @Positive Integer answerId) {
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);

        answerService.likeAnswer(userId, answerId);

        return ApiResponse.success(HttpStatus.OK);
    }
}
