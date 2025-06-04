package com.ssafy.sushi.domain.share.service;

import com.ssafy.sushi.domain.share.entity.ShareToken;
import com.ssafy.sushi.domain.share.repository.ShareTokenRepository;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.sushi.repository.SushiRepository;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShareTokenService {

    private final ShareTokenRepository shareTokenRepository;

    public Integer getSushiIdByToken(String token) {
        ShareToken shareToken = shareTokenRepository.findByToken(token).orElseThrow(() -> new CustomException(ErrorCode.TOKEN_NOT_FOUND));
        return shareToken.getSushi().getId();
    }

}
