package com.djccnt15.spring_board.db.repository;


import com.djccnt15.spring_board.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    
    List<UserEntity> findAllByOrderByIdAsc();
}
