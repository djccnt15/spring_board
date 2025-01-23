package com.djccnt15.spring_board.domain.user.service;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserConverter converter;
    
    public void createUser(UserCreateForm form) {
        var userEntity = UserEntity.builder()
            .username(form.getUsername())
            .password(encoder.encode(form.getPassword1()))
            .email(form.getEmail())
            .build();
        repository.save(userEntity);
    }
    
    @Deprecated
    public UserEntity getUser(String username) {
        return repository.findByUsername(username).orElseThrow(
                () -> new DataNotFoundException("User Not Found")
            );
    }
    
    public UserEntity getUser(UserSession user) {
        return repository.findById(user.getUserId()).orElseThrow(
            () -> new DataNotFoundException("User Not Found")
        );
    }
    
    public Page<UserResponse> getList(int page) {
        var pageable = PageRequest.of(page, 10);
        var userList = repository.findByUsernameIsNotNullOrderById(pageable);
        return userList.map(converter::toResponse);
    }
    
    public void resign(UserEntity user) {
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        repository.save(user);
    }
}
