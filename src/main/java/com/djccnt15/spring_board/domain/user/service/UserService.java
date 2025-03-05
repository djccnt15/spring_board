package com.djccnt15.spring_board.domain.user.service;

import com.djccnt15.spring_board.cache.entity.UserVerifyKeyCache;
import com.djccnt15.spring_board.cache.repository.UserVerifyKeyRepository;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.UserStateEntity;
import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.db.repository.CommentRepository;
import com.djccnt15.spring_board.db.repository.PostRepository;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.db.repository.UserStateRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.*;
import com.djccnt15.spring_board.enums.UserStateEnum;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.FormValidationException;
import com.djccnt15.spring_board.exception.UserVerifyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final UserConverter userConverter;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserStateRepository userStateRepository;
    private final UserVerifyKeyRepository userVerifyKeyRepository;
    
    public UserEntity createUser(UserCreateForm form) {
        var userEntity = UserEntity.builder()
            .username(form.getUsername())
            .password(encoder.encode(form.getPassword1()))
            .email(form.getEmail())
            .build();
        return userRepository.save(userEntity);
    }
    
    public UserEntity getUser(String username) {
        return userRepository.findFirstByUsername(username)
            .orElseThrow(() -> new DataNotFoundException("User Not Found"));
    }
    
    public UserEntity getUser(Long id) {
        return userRepository.findFirstById(id)
            .orElseThrow(() -> new DataNotFoundException("User Not Found"));
    }
    
    public UserEntity getUser(UserSession user) {
        return userRepository.findById(user.getUserId())
            .orElseThrow(() -> new DataNotFoundException("User Not Found"));
    }
    
    public Page<UserEntity> getList(
        int size,
        int page,
        String keyword
    ) {
        var pageable = PageRequest.of(page, size);
        return userRepository.findByKeywordAndUsernameIsNotNull(pageable, keyword);
    }
    
    public void resign(UserEntity user) {
        user.setUsername(null);
        user.setPassword(null);
        user.setEmail(null);
        userRepository.save(user);
    }
    
    public UserEntity validateUpdateForm(
        UserSession user,
        UserUpdateForm form
    ) {
        var userEntity = userRepository.findById(user.getUserId())
            .orElseThrow(() -> new DataNotFoundException("can't find user"));
        if (!encoder.matches(form.getPassword(), userEntity.getPassword())) {
            throw new FormValidationException("비밀번호가 틀렸습니다.");
        } else if (form.getPassword() != null && !form.getPassword1().equals(form.getPassword2())) {
            throw new FormValidationException("2개의 패스워드가 일치하지 않습니다.");
        }
        var userEntityByName = userRepository.findFirstByUsernameAndIdNot(form.getUsername(), user.getUserId());
        userEntityByName.ifPresent(it -> {
            throw new FormValidationException("이미 사용중인 ID입니다.");
        });
        var userEntityByEmail = userRepository.findFirstByEmailAndIdNot(form.getEmail(), user.getUserId());
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
        userRepository.save(user);
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
        userRepository.save(user);
    }
    
    public List<UserPostResponse> getUserPost(
        UserSession user,
        int size,
        int page
    ) {
        var postList = postRepository.getPostListByUserId(user.getUserId(), size, size * page);
        return postList.stream()
            .map(userConverter::toResponse)
            .toList();
    }
    
    public List<UserPostResponse> getUserPost(
        UserEntity user,
        int size,
        int page
    ) {
        var postList = postRepository.getPostListByUserId(user.getId(), size, size * page);
        return postList.stream()
            .map(userConverter::toResponse)
            .toList();
    }
    
    public int getUserPostListCount(UserSession user) {
        return postRepository.countByIsActiveAndAuthorId(true, user.getUserId());
    }
    
    public int getUserPostListCount(UserEntity user) {
        return postRepository.countByIsActiveAndAuthorId(true, user.getId());
    }
    
    public List<UserCommentResponse> getUserComment(
        UserSession user,
        int size,
        int page
    ) {
        var commentList = commentRepository.getCommentListByUserId(user.getUserId(), size, size * page);
        return commentList.stream()
            .map(userConverter::toResponse)
            .toList();
    }
    
    public List<UserCommentResponse> getUserComment(
        UserEntity user,
        int size,
        int page
    ) {
        var commentList = commentRepository.getCommentListByUserId(user.getId(), size, size * page);
        return commentList.stream()
            .map(userConverter::toResponse)
            .toList();
    }
    
    public int getUserCommentListCount(UserSession user) {
        return commentRepository.countByIsActiveAndAuthorId(true, user.getUserId());
    }
    
    public int getUserCommentListCount(UserEntity user) {
        return commentRepository.countByIsActiveAndAuthorId(true, user.getId());
    }
    
    public boolean validateAdmin(UserSession user) {
        return user.getRole() != null && user.getRole().equals(UserRoleEnum.ADMIN);
    }
    
    public boolean validateManager(UserEntity entity) {
        if (entity.getRole() == null) return false;
        
        return switch (entity.getRole()) {
            case ADMIN, STAFF -> true;
            case USER -> false;
            default -> throw new IllegalStateException("Unexpected value: " + entity.getRole());
        };
    }
    
    public UserEntity setState(UserEntity user) {
        var userState = userStateRepository.findByUser(user);
        for (var state : userState) setState(user, state);
        return user;
    }
    
    public void setState(UserResponse user) {
        var userState = userStateRepository.findByUserId(user.getId());
        for (var state : userState) setState(user, state);
    }
    
    private void setState(UserEntity user, UserStateEntity state) {
        var stateId = state.getState().getId();
        if (stateId.equals(UserStateEnum.LOCKED.getId())) user.setLocked(true);
        else if (stateId.equals(UserStateEnum.BANNED.getId())) user.setBanned(true);
    }
    
    private void setState(UserResponse user, UserStateEntity state) {
        var stateId = state.getState().getId();
        if (stateId.equals(UserStateEnum.LOCKED.getId())) {
            user.setLocked(true);
            user.setLockedReason(state.getDetail());
        } else if (stateId.equals(UserStateEnum.BANNED.getId())) {
            user.setBanned(true);
            user.setBannedReason(state.getDetail());
        }
    }
    
    public void saveUserVerifyKey(UserVerifyKeyCache key) {
        userVerifyKeyRepository.save(key);
    }
    
    public UserVerifyKeyCache getUserVerifyKey(Long id) {
        return userVerifyKeyRepository.findById(id)
            .orElseThrow(() -> new UserVerifyException("verify token is expired"));
    }
    
    public void verifyUser(UserEntity user) {
        user.setVerified(true);
        userRepository.save(user);
    }
    
    public void deleteUserVerifyKey(Long id) {
        userVerifyKeyRepository.deleteById(id);
    }
}
