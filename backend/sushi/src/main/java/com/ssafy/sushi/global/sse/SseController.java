package com.ssafy.sushi.global.sse;

import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.error.exception.CustomException;
import com.ssafy.sushi.global.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sse")
public class SseController {

    private final AuthenticationUtil authenticationUtil;
    private final SseService sseService;

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            Integer userId = authenticationUtil.getCurrentUserId(userPrincipal);
            return sseService.subscribe(userId);
        } catch (CustomException e) {
            log.info("SSE Access Denied catch User Notification");
//            SseEmitter emitter = new SseEmitter(0L);
//            emitter.complete();
//            return emitter;
            return null;
        }
    }
}
