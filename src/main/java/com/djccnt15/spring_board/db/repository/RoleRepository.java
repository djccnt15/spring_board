package com.djccnt15.spring_board.db.repository;

import com.djccnt15.spring_board.db.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
