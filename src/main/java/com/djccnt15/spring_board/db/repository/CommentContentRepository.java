package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CommentContentEntity;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentContentRepository extends JpaRepository<CommentContentEntity, Long> {
    
    Optional<CommentContentEntity> findFirstByCommentOrderByIdDesc(CommentEntity comment);
}
