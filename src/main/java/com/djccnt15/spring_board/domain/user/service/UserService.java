package com.djccnt15.spring_board.domain.user.service;

import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import com.djccnt15.spring_board.domain.user.model.UserRecoveryForm;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.domain.user.model.UserUpdateForm;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.FormValidationException;
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
    
    public UserEntity validateUpdateForm(
        UserSession user,
        UserUpdateForm form
    ) {
        var userEntity = repository.findById(user.getUserId()).orElseThrow(
            () -> new DataNotFoundException("can't find user")
        );
        if (!encoder.matches(form.getPassword(), userEntity.getPassword())) {
            throw new FormValidationException("비밀번호가 틀렸습니다.");
        } else if (form.getPassword() != null && !form.getPassword1().equals(form.getPassword2())) {
            throw new FormValidationException("2개의 패스워드가 일치하지 않습니다.");
        }
        var userEntityByName = repository.findByUsernameAndIdNot(form.getUsername(), user.getUserId());
        userEntityByName.ifPresent(it -> {
            throw new FormValidationException("이미 사용중인 ID입니다.");
        });
        var userEntityByEmail = repository.findByEmailAndIdNot(form.getEmail(), user.getUserId());
        userEntityByEmail.ifPresent(it -> {
            throw new FormValidationException("이미 사용중인 Email입니다.");
        });
        return userEntity;
    }
    
    public void updateUser(
        UserEntity user,
        UserUpdateForm form
    ) {
        user.setUsername(form.getUsername());
        user.setEmail(form.getEmail());
        if (!form.getPassword1().isEmpty()) {
            user.setPassword(encoder.encode(form.getPassword1()));
        }
        repository.save(user);
    }
    
    public boolean validateRecoverEmail(
        UserRecoveryForm form,
        UserEntity user
    ) {
        return form.getEmail().equals(user.getEmail());
    }
    
    public void recoverUser(
        UserEntity user,
        String password
    ) {
        user.setPassword(encoder.encode(password));
        repository.save(user);
    }
}
