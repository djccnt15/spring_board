package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.db.repository.QuestionRepository;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    
    private final QuestionRepository repository;
    
    public void create(QuestionForm form) {
        var entity = QuestionEntity.builder()
            .subject(form.getSubject())
            .content(form.getSubject())
            .build();
        repository.save(entity);
    }
}
