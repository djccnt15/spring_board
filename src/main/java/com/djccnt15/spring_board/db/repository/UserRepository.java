package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);
    
    Page<UserEntity> findByUsernameIsNotNullOrderById(Pageable pageable);
}
