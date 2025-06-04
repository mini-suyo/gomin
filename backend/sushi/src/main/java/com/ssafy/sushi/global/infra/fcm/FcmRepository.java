package com.ssafy.sushi.global.infra.fcm;

import com.ssafy.sushi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FcmRepository extends JpaRepository<FcmToken, Long> {
    Optional<FcmToken> findByUser(User user);

    List<FcmToken> findAllByUserId(Integer userId);

    boolean existsByToken(String token);

    void deleteByToken(String token);
}
