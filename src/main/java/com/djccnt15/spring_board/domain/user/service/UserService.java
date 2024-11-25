package com.djccnt15.spring_board.domain.user.service;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    
    public void create(UserCreateForm form) {
        var userEntity = UserEntity.builder()
            .username(form.getUsername())
            .password(encoder.encode(form.getPassword1()))
            .email(form.getEmail())
            .build();
        repository.save(userEntity);
    }
}
