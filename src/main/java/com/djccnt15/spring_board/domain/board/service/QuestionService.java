package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.QuestionRepository;
import com.djccnt15.spring_board.domain.board.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import com.djccnt15.spring_board.domain.board.model.QuestionResponse;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    
    private final QuestionRepository repository;
    private final QuestionConverter converter;
    
    public void create(
        QuestionForm form,
        UserEntity user
    ) {
        var entity = QuestionEntity.builder()
            .subject(form.getSubject())
            .content(form.getContent())
            .author(user)
            .build();
        repository.save(entity);
    }
    
    public Page<QuestionResponse> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        var pageable = PageRequest.of(page, 10, Sort.by(sorts));
        var pageableEntityList = repository.findAll(pageable);
        return pageableEntityList.map(converter::toResponse);
    }
    
    public QuestionEntity getDetail(Long id) {
        return repository
            .findById(id)
            .orElseThrow(
                () -> new DataNotFoundException(String.format("not exist task id: %d", id))
            );
    }
    
    public void update(
        QuestionEntity entity,
        QuestionForm form
    ) {
        entity.setSubject(form.getSubject());
        entity.setContent(form.getContent());
        repository.save(entity);
    }
    
    public void delete(QuestionEntity question) {
        repository.delete(question);
    }
    
    public void validateAuthor(
        QuestionEntity entity,
        Principal principal
    ) {
        if (!entity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
    }
    
    public void vote(
        QuestionEntity question,
        UserEntity user
    ) {
        question.getVoter().add(user);
        repository.save(question);
    }
}
