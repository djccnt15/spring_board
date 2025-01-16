package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.PostContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostContentRepository extends JpaRepository<PostContentEntity, Long> {
    
    Optional<PostContentEntity> findFirstByPostIdOrderByIdDesc(Long id);
}
