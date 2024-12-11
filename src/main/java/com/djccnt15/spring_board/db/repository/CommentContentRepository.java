package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CommentContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentContentRepository extends JpaRepository<CommentContentEntity, Long> {
}
