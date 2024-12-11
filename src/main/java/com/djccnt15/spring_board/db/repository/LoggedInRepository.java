package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.LoggedInEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoggedInRepository extends JpaRepository<LoggedInEntity, Long> {
}
