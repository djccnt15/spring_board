package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
