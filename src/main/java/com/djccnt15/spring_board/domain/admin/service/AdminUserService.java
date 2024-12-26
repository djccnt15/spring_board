package com.djccnt15.spring_board.domain.admin.service;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    
    private final UserRepository userRepository;
    
    public void grantManager(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(
            () -> new DataNotFoundException("can't find user")
        );
        userEntity.setRole(UserRoleEnum.STAFF);
        userRepository.save(userEntity);
    }
    
    public void revokeManager(Long id) {
        var userEntity = userRepository.findById(id).orElseThrow(
            () -> new DataNotFoundException("can't find user")
        );
        userEntity.setRole(null);
        userRepository.save(userEntity);
    }
}
