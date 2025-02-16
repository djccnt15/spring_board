package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.UserStateEntity;
import com.djccnt15.spring_board.db.entity.id.UserStateId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserStateRepository extends JpaRepository<UserStateEntity, UserStateId> {
    
    Set<UserStateEntity> findByUser(UserEntity user);
}
