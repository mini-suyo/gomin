-- 고양이 마스터
INSERT INTO user (nickname,total_likes, provider, provider_id, created_at, updated_at)
VALUES ('고양이마스터', 0, 'KAKAO', 1111111, NOW(), NOW());

-- 더미 유저
INSERT INTO user (nickname, total_likes, provider, provider_id, created_at, updated_at)
VALUES ('테스트맨1', 0,'KAKAO', 112312312, NOW(), NOW()),
        ('테스트맨2', 0, 'KAKAO', 1231440, NOW(), NOW());

# -- 초밥(고민) 데이터
# -- 현재 시각 기준으로 다양한 상태의 데이터 생성
# INSERT INTO sushi (id, user_id, category_id, sushi_type_id,
#                    title, content, expire_time, max_answers,
#                    remaining_answers, is_closed, created_at, updated_at)
# VALUES
# -- 활성화된 초밥 (유통기한 충분, 답변 가능)
# (1, 2, 1, 1, '연애 조언 부탁드려요', '썸 타는 중인데 고백할까요?',
#  DATE_ADD(NOW(), INTERVAL 2 HOUR), 5, 5, false, NOW(), NOW()),
# (2, 2, 2, 2, '친구와 싸웠어요', '어떻게 화해하면 좋을까요?',
#  DATE_ADD(NOW(), INTERVAL 1 HOUR), 3, 3, false, NOW(), NOW()),
# (3, 3, 3, 3, '부모님과 갈등이에요', '세대차이를 어떻게 극복하나요?',
#  DATE_ADD(NOW(), INTERVAL 3 HOUR), 4, 4, false, NOW(), NOW()),
#
# -- 곧 마감될 초밥 (유통기한 10분 이내)
# (4, 2, 4, 4, '직장 스트레스', '이직을 고민중입니다.',
#  DATE_ADD(NOW(), INTERVAL 5 MINUTE), 3, 2, false, NOW(), NOW()),
#
# -- 이미 마감된 초밥
# (5, 3, 5, 5, '시험 불안감', '어떻게 극복하나요?',
#  DATE_ADD(NOW(), INTERVAL -1 HOUR), 2, 0, true, NOW(), NOW()),
#
# (6, 3, 2, 3, '친구 생일 선물', '20만원 예산으로 뭘 사줄까요?',
#  DATE_ADD(NOW(), INTERVAL 4 HOUR), 4, 4, false, NOW(), NOW()),
#
# (7, 2, 1, 4, '첫 소개팅 장소 추천', '이번 주말에 소개팅인데 어디로 갈까요?',
#  DATE_ADD(NOW(), INTERVAL 5 HOUR), 5, 5, false, NOW(), NOW());
#
# -- 답변 데이터
# INSERT INTO answer (id, user_id, sushi_id, content, is_liked, created_at, updated_at, is_negative)
# VALUES (1, 2, 1, '용기내서 고백해보세요!', false, NOW(), NOW(), false),
#        (2, 3, 2, '먼저 연락해서 만나보는게 어떨까요?', true, NOW(), NOW(), false),
#        (3, 3, 5, '심호흡하고 차근차근 준비해보세요.', true, NOW(), NOW(), false);
