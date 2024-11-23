package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.db.repository.AnswerRepository;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    
    private final AnswerRepository repository;
    
    public void create(
        QuestionEntity question,
        AnswerForm form
    ) {
        var answer = AnswerEntity.builder()
            .content(form.getContent())
            .questionEntity(question)
            .build();
        repository.save(answer);
    }
}
