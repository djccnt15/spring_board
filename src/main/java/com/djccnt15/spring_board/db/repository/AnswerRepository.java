package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
}
