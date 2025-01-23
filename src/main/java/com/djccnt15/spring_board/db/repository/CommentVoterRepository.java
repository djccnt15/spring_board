package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.CommentVoterEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.id.CommentVoterId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentVoterRepository extends JpaRepository<CommentVoterEntity, CommentVoterId> {
    
    Optional<CommentVoterEntity> findByCommentAndUser(CommentEntity comment, UserEntity user);
}
