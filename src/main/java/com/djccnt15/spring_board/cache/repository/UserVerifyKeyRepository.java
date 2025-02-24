package com.djccnt15.spring_board.cache.repository;

import com.djccnt15.spring_board.cache.entity.UserVerifyKeyCache;
import org.springframework.data.repository.CrudRepository;

public interface UserVerifyKeyRepository extends CrudRepository<UserVerifyKeyCache, Long> {
}
