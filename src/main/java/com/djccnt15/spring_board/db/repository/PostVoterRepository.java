package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.PostVoterEntity;
import com.djccnt15.spring_board.db.entity.id.PostVoterId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostVoterRepository extends JpaRepository<PostVoterEntity, PostVoterId> {
}
