package com.ssafy.sushi.domain.sushi.repository;

import com.ssafy.sushi.domain.sushi.entity.SuShiExposure;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SushiExposureRepository extends JpaRepository<SuShiExposure, Long> {

    SuShiExposure findByUserIdAndSushiId(Integer userId, Integer sushiId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT se FROM SuShiExposure se WHERE se.user.id = :userId AND se.sushi.id = :sushiId")
    Optional<SuShiExposure> findByUserIdAndSushiIdWithLock(@Param("userId") Integer userId, @Param("sushiId") Integer sushiId);

    @Modifying
    @Query(value = """
            INSERT INTO sushi_exposure (user_id, sushi_id, timestamp)
            VALUES (:userId, :sushiId, :timestamp)\s
            ON DUPLICATE KEY UPDATE timestamp = :timestamp
           """, nativeQuery = true)
    void insertOrUpdateExposure(
            @Param("userId") Integer userId,
            @Param("sushiId") Integer sushiId,
            @Param("timestamp") LocalDateTime timestamp
    );
}
