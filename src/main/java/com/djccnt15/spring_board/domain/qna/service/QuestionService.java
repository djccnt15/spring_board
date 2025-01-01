package com.djccnt15.spring_board.domain.qna.service;

import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.QuestionRepository;
import com.djccnt15.spring_board.domain.qna.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.qna.model.QuestionForm;
import com.djccnt15.spring_board.domain.qna.model.QuestionResponse;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
    
    public Page<QuestionResponse> getList(
        int page,
        String keyword
    ) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        var pageable = PageRequest.of(page, 10, Sort.by(sorts));
        var pageableEntityList = repository.findAllByKeyword(keyword, pageable);
        return pageableEntityList.map(converter::toResponse);
    }
    
    public QuestionEntity getDetail(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new DataNotFoundException("not exist task id: %d".formatted(id))
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
    
    @Deprecated
    public Specification<QuestionEntity> searchSpec(String keyword) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;
            
            @Override
            public Predicate toPredicate(
                Root<QuestionEntity> q,
                CriteriaQuery<?> query,
                CriteriaBuilder cb
            ) {
                query.distinct(true);  // 중복을 제거
                Join<QuestionEntity, UserEntity> u1 = q.join("author", JoinType.LEFT);
                Join<QuestionEntity, UserEntity> a = q.join("answerEntityList", JoinType.LEFT);
                Join<AnswerEntity, UserEntity> u2 = a.join("author", JoinType.LEFT);
                return cb.or(cb.like(q.get("subject"), "%" + keyword + "%"), // 제목
                    cb.like(q.get("content"), "%" + keyword + "%"),      // 내용
                    cb.like(u1.get("username"), "%" + keyword + "%"),    // 질문 작성자
                    cb.like(a.get("content"), "%" + keyword + "%"),      // 답변 내용
                    cb.like(u2.get("username"), "%" + keyword + "%"));   // 답변 작성자
            }
        };
    }
}
