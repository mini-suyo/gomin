package com.ssafy.sushi.global.config;

import com.ssafy.sushi.domain.auth.service.strategy.GoogleOAuthStrategy;
import com.ssafy.sushi.domain.auth.service.strategy.KakaoOAuthStrategy;
import com.ssafy.sushi.domain.auth.service.strategy.OAuthStrategy;
import com.ssafy.sushi.domain.user.enums.Provider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
public class OAuthConfig {
    @Bean
    public Map<Provider, OAuthStrategy> oAuthStrategyMap(
            KakaoOAuthStrategy kakaoStrategy,
            GoogleOAuthStrategy googleStrategy
    ) {
        Map<Provider, OAuthStrategy> strategyMap = new EnumMap<>(Provider.class);
        strategyMap.put(Provider.KAKAO, kakaoStrategy);
        strategyMap.put(Provider.GOOGLE, googleStrategy);
        return strategyMap;
    }
}
