package com.djccnt15.spring_board;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class QuestionCreateTest {
    
    @Autowired
    private QuestionService questionService;
    
    @Autowired
    private PasswordEncoder encoder;
    
    @Test
    void testJpa() {
        var user = UserEntity.builder()
            .username("test")
            .password(encoder.encode("test"))
            .email("test@b.com")
            .build();
        
        for (int i = 1; i <= 300; i++) {
            var subject = "테스트 데이터입니다:[%03d]".formatted(i);
            var content = "내용무";
            var question = QuestionForm.builder()
                .subject(subject)
                .content(content)
                .build();
            questionService.create(question, user);
        }
    }
}
