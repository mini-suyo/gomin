package com.ssafy.sushi.domain.answer.entity;

import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.global.common.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "answer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "answer_userId_sushiId_unique",
                        columnNames = {"user_id", "sushi_id"})})
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Answer extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "sushi_id")
    private Sushi sushi;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_liked", nullable = false)
    @Builder.Default
    private Boolean isLiked = false;

    @Column(name = "is_negative", nullable = false)
    @Builder.Default
    private Boolean isNegative = false;

    @Column(name = "is_gpt")
    @Builder.Default
    private Boolean isGPT = false;

    public void markAsLiked() {
        this.isLiked = true;
    }
}
