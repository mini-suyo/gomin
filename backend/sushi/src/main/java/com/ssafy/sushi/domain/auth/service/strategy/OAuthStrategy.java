package com.ssafy.sushi.domain.auth.service.strategy;

import com.ssafy.sushi.domain.auth.dto.OAuthUserInfo;

import java.io.IOException;

public interface OAuthStrategy {
    OAuthUserInfo getUserInfo(String code) throws IOException;
}

