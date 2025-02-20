package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.UserStateEntity;
import com.djccnt15.spring_board.db.entity.id.UserStateId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserStateRepository extends JpaRepository<UserStateEntity, UserStateId> {
    
    List<UserStateEntity> findByUser(UserEntity user);
    
    List<UserStateEntity> findByUserId(Long id);
}
