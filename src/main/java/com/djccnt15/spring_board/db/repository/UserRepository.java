package com.djccnt15.spring_board.db.repository;


import com.djccnt15.spring_board.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
