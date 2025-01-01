package com.djccnt15.spring_board.domain.qna.service;

import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.AnswerRepository;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.domain.qna.model.AnswerForm;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnswerService {
    
    private final AnswerRepository repository;
    
    public AnswerEntity create(
        QuestionEntity question,
        AnswerForm form,
        UserEntity user
    ) {
        var answer = AnswerEntity.builder()
            .content(form.getContent())
            .questionEntity(question)
            .author(user)
            .build();
        return repository.save(answer);
    }
    
    public AnswerEntity getAnswer(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("answer not found")
            );
    }
    
    public AnswerEntity update(
        AnswerEntity entity,
        AnswerForm form
    ) {
        entity.setContent(form.getContent());
        return repository.save(entity);
    }
    
    public void validateAuthor(
        AnswerEntity entity,
        Principal principal
    ) {
        if (!entity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
    }
    
    public void delete(AnswerEntity entity) {
        repository.delete(entity);
    }
    
    public void vote(
        AnswerEntity answer,
        UserEntity user
    ) {
        answer.getVoter().add(user);
        repository.save(answer);
    }
}
