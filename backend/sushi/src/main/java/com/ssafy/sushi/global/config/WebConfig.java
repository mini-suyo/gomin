package com.ssafy.sushi.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.domain}")
    private String domain;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:5173", domain)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("Authorization", "Content-Type", "X-Requested-With",
                        "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials","Access-Control-Allow-Headers",
                        "Accept", "Origin", "Cookie", "Set-Cookie",
                        "Cache-Control", "Connection")  // 헤더 추가
                .exposedHeaders("Set-Cookie")  // 쿠키 노출 허용
                .allowCredentials(true)
                .maxAge(3600);
    }
}
