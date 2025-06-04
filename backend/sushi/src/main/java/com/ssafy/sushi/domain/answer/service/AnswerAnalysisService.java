package com.ssafy.sushi.domain.answer.service;

import com.ssafy.sushi.global.error.exception.CustomException;
import com.ssafy.sushi.global.infra.chatgpt.GPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerAnalysisService {

    private final GPTService gptService;

    public Boolean analyzeSentiment(String title, String content, String text) {
        // GPT에게 윤리성 분석을 요청하고, 분석 결과에 따라 부정적이라면 true, 아니면 false를 반환
        String sentimentAnalysis;
        try {
            sentimentAnalysis = gptService.analyzeNegative(title, content, text);
        } catch (CustomException e) {
            sentimentAnalysis = "positive";
        }

        // "negative" 결과가 있으면 부정적, 그 외에는 긍정적이라고 판단
        return "negative".equalsIgnoreCase(sentimentAnalysis);
    }
}
