package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findBySubject(String subject);
    
    QuestionEntity findBySubjectAndContent(String subject, String content);
    
    List<QuestionEntity> findBySubjectLike(String subject);
    
    Page<QuestionEntity> findAll(Pageable pageable);
    
    Page<QuestionEntity> findAll(Specification<QuestionEntity> spec, Pageable pageable);
}
