package com.ssafy.sushi.domain.share.repository;

import com.ssafy.sushi.domain.share.entity.ShareToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShareTokenRepository extends JpaRepository<ShareToken, Integer> {
    Optional<ShareToken> findByToken(String token);  // 토큰으로 게시물 조회
}
