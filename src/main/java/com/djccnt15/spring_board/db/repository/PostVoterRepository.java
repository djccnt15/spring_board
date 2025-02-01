package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.PostVoterEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.id.PostVoterId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostVoterRepository extends JpaRepository<PostVoterEntity, PostVoterId> {
    
    Optional<PostVoterEntity> findFirstByPostAndUser(PostEntity post, UserEntity user);
}
