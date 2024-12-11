package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<StateEntity, Long> {
}
