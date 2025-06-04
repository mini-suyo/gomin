package com.ssafy.sushi.domain.sushi.entity;

import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.global.common.Entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sushi")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Sushi extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; //초밥 등록 유저

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category; //고민 카테고리

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sushi_type_id")
    private SushiType sushiType; //초밥 종류

    @Column(nullable = false, length = 100)
    private String title; //제목

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content; //내용

    @Column(name = "expire_time", nullable = false)
    private LocalDateTime expirationTime; //유통기한

    @Column(name = "max_answers", nullable = false)
    private Integer maxAnswers; //초기 설정 답변 인원수

    @Column(name = "remaining_answers", nullable = false)
    private Integer remainingAnswers; //답변 가능한 인원수

    @Column(name = "is_closed", nullable = false)
    @Builder.Default
    private Boolean isClosed = false; //초밥 마감 여부 (기본값 설정)

    // isClosed를 true로 변경하는 비즈니스 로직 메소드
    public void closeSushi() {
        this.isClosed = true;
    }

    // 답변 가능한 인원수 -1
    public void reduceRemainingAnswers(){
        this.remainingAnswers -= 1;
    }

    public boolean canAnswer() {
        return !isClosed && remainingAnswers > 0;
    }
}
