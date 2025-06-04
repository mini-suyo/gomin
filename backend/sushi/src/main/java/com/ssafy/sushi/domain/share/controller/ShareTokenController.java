package com.ssafy.sushi.domain.share.controller;

import com.ssafy.sushi.domain.share.service.ShareTokenService;
import com.ssafy.sushi.domain.sushi.dto.response.SushiOnRailResponse;
import com.ssafy.sushi.domain.sushi.service.SushiService;
import com.ssafy.sushi.global.common.response.ApiResponse;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/share")
public class ShareTokenController {

    private final AuthenticationUtil authenticationUtil;
    private final ShareTokenService shareTokenService;
    private final SushiService sushiService;

    @GetMapping("/{token}")
    public ResponseEntity<ApiResponse<SushiOnRailResponse>> getShareSushi(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable("token") String token){
        Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);
        Integer sushiId = shareTokenService.getSushiIdByToken(token);

        return ApiResponse.success(sushiService.getRailSushi(userId, sushiId));
    }

}
