package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.PostContentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostContentRepository extends JpaRepository<PostContentEntity, Long> {
}
