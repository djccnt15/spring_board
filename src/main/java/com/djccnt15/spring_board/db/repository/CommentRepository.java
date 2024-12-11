package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
