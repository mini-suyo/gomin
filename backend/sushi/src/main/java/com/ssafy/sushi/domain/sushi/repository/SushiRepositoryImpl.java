package com.ssafy.sushi.domain.sushi.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.sushi.domain.answer.entity.QAnswer;
import com.ssafy.sushi.domain.sushi.entity.QSuShiExposure;
import com.ssafy.sushi.domain.sushi.entity.QSushi;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SushiRepositoryImpl implements SushiCustomRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Sushi> findRandomAvailableSushi(Integer userId, LocalDateTime now, int size) {

        //본인이 작성한 초밥은 제공하지 않음
        //유통기한 10분 남은 초밥은 제공하지 않음
        //사용자에게 마지막으로 노출된지 10분이 지나지 않은 초밥은 제공하지 않음
        //이미 마감된 초밥은 제공하지 않음
        //본인이 답변한 초밥은 제공하지 않음

        // 10분 후 시간 계산 (유통기한 체크용)
        LocalDateTime tenMinutesLater = now.plusMinutes(10);

        // 10분 전 시간 계산 (노출 시간 체크용)
        LocalDateTime tenMinutesAgo = now.minusMinutes(10);

        QSushi sushi = QSushi.sushi;
        QAnswer answer = QAnswer.answer;
        QSuShiExposure exposure = QSuShiExposure.suShiExposure;

        // 서브쿼리: 사용자가 답변한 초밥 ID 목록
        JPAQuery<Integer> answeredSushiIds = queryFactory
                .select(answer.sushi.id)
                .from(answer)
                .where(answer.user.id.eq(userId));

        // 서브쿼리: 최근 10분 이내에 노출된 초밥 ID 목록
        JPAQuery<Integer> recentlyExposedSushiIds = queryFactory
                .select(exposure.sushi.id)
                .from(exposure)
                .where(
                        exposure.user.id.eq(userId)
                                .and(exposure.timestamp.gt(tenMinutesAgo))
                );

        return queryFactory
                .selectFrom(sushi)
                .where(
                        sushi.user.id.ne(userId) // 본인이 작성한 초밥 제외
                                .and(sushi.expirationTime.gt(tenMinutesLater)) // 유통기한 10분 이상 남은 것만
                                .and(sushi.isClosed.eq(false)) // 마감되지 않은 것만
                                .and(sushi.remainingAnswers.gt(0)) // 답변 가능한 수가 남아있는 것만
                                .and(sushi.id.notIn(answeredSushiIds)) // 이미 답변한 초밥 제외
                                .and(sushi.id.notIn(recentlyExposedSushiIds)) // 최근 노출된 초밥 제외
                )
                .orderBy(Expressions.numberTemplate(Double.class, "function('RAND')").asc()) // 무작위 정렬
                .limit(size)
                .fetch();
    }
}
