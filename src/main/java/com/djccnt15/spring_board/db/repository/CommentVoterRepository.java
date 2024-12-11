package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.CommentVoterEntity;
import com.djccnt15.spring_board.db.entity.id.CommentVoterId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentVoterRepository extends JpaRepository<CommentVoterEntity, CommentVoterId> {
}
