package com.ssafy.sushi.global.config;

import com.ssafy.sushi.global.sse.SseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component  // 새로운 클래스 생성
@Slf4j
@RequiredArgsConstructor
public class ShutdownManager {
    private final SseService sseService;

    @EventListener(ContextClosedEvent.class)
    public void handleContextClose() {
        log.info("Context shutdown signal received - cleaning up SSE connections");
        sseService.destroy();  // 기존 destroy 메서드 호출
    }
}
