package com.ssafy.sushi.global.common.util;

import com.ssafy.sushi.global.security.UserPrincipal;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.crypto.SecretKey;
import java.util.Collections;

public class TestUserMaker {

    @Value("${jwt.secret}")
    private String secretKey;

//    private final long tokenValidTime = 24 * 60 * 60 * 1000L; // 24시간

    public static Authentication getAuthentication(String token) {
        Integer userId;
        if ("test".equals(token)) {
            userId = 2;
        } else if ("test2".equals(token)) {
            userId = 3;
        } else {
            return null;
        }

        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(userId)
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .build();

        return new UsernamePasswordAuthenticationToken(
                userPrincipal,
                "",
                userPrincipal.getAuthorities());
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
