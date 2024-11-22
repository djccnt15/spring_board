package com.djccnt15.spring_board;

import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestionCreateTest {
    
    @Autowired
    private QuestionService questionService;
    
    @Test
    void testJpa() {
        for (int i = 1; i <= 300; i++) {
            var subject = String.format("테스트 데이터입니다:[%03d]", i);
            var content = "내용무";
            var question = QuestionForm.builder()
                .subject(subject)
                .content(content)
                .build();
            questionService.create(question);
        }
    }
}