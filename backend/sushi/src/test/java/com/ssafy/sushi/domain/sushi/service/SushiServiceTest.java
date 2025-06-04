package com.ssafy.sushi.domain.sushi.service;

import com.ssafy.sushi.domain.answer.repository.AnswerRepository;
import com.ssafy.sushi.domain.answer.entity.Answer;
import com.ssafy.sushi.domain.sushi.dto.response.MySushiDetailResponse;
import com.ssafy.sushi.domain.sushi.entity.Category;
import com.ssafy.sushi.domain.sushi.entity.Sushi;
import com.ssafy.sushi.domain.sushi.entity.SushiType;
import com.ssafy.sushi.domain.sushi.repository.SushiRepository;
import com.ssafy.sushi.domain.user.entity.User;
import com.ssafy.sushi.domain.user.enums.Provider;
import com.ssafy.sushi.global.error.ErrorCode;
import com.ssafy.sushi.global.error.exception.CustomException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SushiServiceTest {

    @InjectMocks
    private SushiService sushiService;

    @Mock
    private SushiRepository sushiRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Test
    @DisplayName("내가 작성한 초밥 상세 조회")
    void getMySushiDetail() {

        // given
        Integer userId = 1;
        Integer sushiId = 1;

        User user = getUser();
        ReflectionTestUtils.setField(user, "id", 1);

        Sushi sushi = getSushi(user);
        ReflectionTestUtils.setField(sushi, "id", 1);

        List<Answer> answerList = getAnswerList();

        when(sushiRepository.findById(sushiId)).thenReturn(Optional.of(sushi));
        when(answerRepository.findBySushiId(sushiId)).thenReturn(answerList);

        // when
        MySushiDetailResponse response = sushiService.getMySushiDetail(userId, sushiId);

        // then
        assertAll(
                () -> assertEquals(sushiId, response.getSushiId()),
                () -> assertEquals("title", response.getTitle()),
                () -> assertEquals("content", response.getContent()),
                () -> assertEquals(1, response.getCategory()),
                () -> assertEquals(1, response.getSushiType()),
                () -> assertEquals(10, response.getMaxAnswers()),
                () -> assertEquals(5, response.getRemainingAnswers()),
                () -> assertFalse(response.getIsClosed()),
                () -> assertEquals(1, response.getAnswer().size()),
                () -> assertEquals("contentAnswer", response.getAnswer().get(0).getContent())
        );

        verify(sushiRepository).findById(sushiId);
        verify(answerRepository).findBySushiId(sushiId);

    }

    @Test
    @DisplayName("다른 사용자의 초밥을 조회하면 예외가 발생한다")
    void getMySushiDetail_Unauthorized() {
        // given
        Integer userId = 1;
        Integer otherUserId = 2;
        Integer sushiId = 1;

        User user = getUser();
        ReflectionTestUtils.setField(user, "id", userId);

        User otherUser = getUser();
        ReflectionTestUtils.setField(otherUser, "id", otherUserId);

        Sushi sushi = getSushi(otherUser);

        when(sushiRepository.findById(sushiId)).thenReturn(Optional.of(sushi));

        // when & then
        CustomException exception = assertThrows(CustomException.class, () ->
                sushiService.getMySushiDetail(userId, sushiId)
        );

        assertEquals(ErrorCode.UNAUTHORIZED_SUSHI_ACCESS, exception.getErrorCode());
        verify(sushiRepository).findById(sushiId);
        verify(answerRepository, never()).findBySushiId(any());
    }



    private List<Answer> getAnswerList() {
        return List.of(
                Answer.builder()
                        .content("contentAnswer")
                        .isLiked(false)
                        .build()
        );
    }

    private User getUser() {
        return User.builder()
                .nickname("nickname")
                .provider(Provider.GOOGLE)
                .providerId("providerId")
                .build();
    }

    private Sushi getSushi(User user) {
        return Sushi.builder()
                .user(user)
                .title("title")
                .content("content")
                .category(Category.builder().id(1).build())
                .sushiType(SushiType.builder().id(1).build())
                .maxAnswers(10)
                .remainingAnswers(5)
                .isClosed(false)
                .expirationTime(LocalDateTime.now().plusDays(7))
                .build();
    }
}