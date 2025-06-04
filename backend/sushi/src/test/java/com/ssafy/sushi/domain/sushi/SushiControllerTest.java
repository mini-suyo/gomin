package com.ssafy.sushi.domain.sushi;

import com.ssafy.sushi.domain.answer.service.AnswerService;
import com.ssafy.sushi.domain.sushi.controller.SushiController;
import com.ssafy.sushi.domain.sushi.dto.response.MySushiListResponse;
import com.ssafy.sushi.domain.sushi.service.SushiService;
import com.ssafy.sushi.global.common.CustomPage;
import com.ssafy.sushi.global.common.util.AuthenticationUtil;
import com.ssafy.sushi.global.security.UserPrincipal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SushiController.class)
class SushiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SushiService sushiService;

    @MockitoBean
    private AnswerService answerService;

    @MockitoBean
    private AuthenticationUtil authenticationUtil;

    @MockitoBean // EnableJpaAuditing 어노테이션을 사용하기 위해 추가
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("내 초밥 목록 조회")
    void getMySushiList() throws Exception {
        // given

        Integer userId = 1;
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .id(userId)
                .authorities(Collections.emptyList())
                .build();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userPrincipal,
                null,
                userPrincipal.getAuthorities()
        );

        Page<MySushiListResponse> page = new PageImpl<>(createSushiList());
        CustomPage<MySushiListResponse> expectedPage = new CustomPage<>(page);

        doReturn(expectedPage)
                .when(sushiService)
                .getMySushiList(any(), any(), any(Pageable.class));

        // when
        ResultActions result = mockMvc.perform(get("/api/sushi/my")
                .with(SecurityMockMvcRequestPostProcessors.authentication(authentication))
                .param("page", "0")
                .param("size", "10"));


        // then
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.content").exists())
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content.length()").value(10))
                .andExpect(jsonPath("$.data.content[0].sushiId").exists())
                .andExpect(jsonPath("$.data.content[0].category").exists());
    }


    private List<MySushiListResponse> createSushiList() {
        List<MySushiListResponse> sushiList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            sushiList.add(MySushiListResponse.builder()
                    .sushiId(i)
                    .category(i)
                    .sushiType(i)
                    .remainingAnswers(i)
                    .expirationTime(LocalDateTime.now())
                    .build());
        }
        return sushiList;
    }
}