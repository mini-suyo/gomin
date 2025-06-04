package com.ssafy.sushi.domain.sushi.entity;

import com.ssafy.sushi.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Entity
@Table(name = "sushi_exposure",
        uniqueConstraints = {
        @UniqueConstraint(
                name = "sushi_exposure_userId_sushiId_unique",
                columnNames = {"user_id", "sushi_id"})})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SuShiExposure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sushi_id", nullable = false)
    private Sushi sushi;

    private LocalDateTime timestamp;

    public void updateTimestamp(){
        this.timestamp = now();
    }

}
