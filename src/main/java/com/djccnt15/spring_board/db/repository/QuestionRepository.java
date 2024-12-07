package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.QuestionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    QuestionEntity findBySubject(String subject);
    
    QuestionEntity findBySubjectAndContent(String subject, String content);
    
    List<QuestionEntity> findBySubjectLike(String subject);
    
    Page<QuestionEntity> findAll(Pageable pageable);
    
    @Deprecated
    Page<QuestionEntity> findAll(Specification<QuestionEntity> spec, Pageable pageable);
    
    @Query("select "
        + "distinct q "
        + "from QuestionEntity q "
        + "left outer join UserEntity u1 on q.author=u1 "
        + "left outer join AnswerEntity a on a.questionEntity=q "
        + "left outer join UserEntity u2 on a.author=u2 "
        + "where "
        + "   q.subject like %:kw% "
        + "   or q.content like %:kw% "
        + "   or u1.username like %:kw% "
        + "   or a.content like %:kw% "
        + "   or u2.username like %:kw% ")
    Page<QuestionEntity> findAllByKeyword(@Param("kw") String keyword, Pageable pageable);
}
