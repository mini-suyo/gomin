package com.ssafy.sushi.domain.user.repository;

import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.domain.user.enums.Provider;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByProviderAndProviderId(Provider provider, String providerId);

    Optional<User> findById(Integer id);

    // 삭제된 사용자를 포함하여 조회
    @Query("SELECT u FROM User u WHERE u.provider = :provider AND u.providerId = :providerId")
    Optional<User> findByProviderAndProviderIdIncludeDeleted(
            @Param("provider") Provider provider,
            @Param("providerId") String providerId);

}