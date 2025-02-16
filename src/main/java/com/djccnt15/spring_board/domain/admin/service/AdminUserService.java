package com.djccnt15.spring_board.domain.admin.service;

import com.djccnt15.spring_board.db.entity.StateEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.db.repository.StateRepository;
import com.djccnt15.spring_board.db.repository.UserRepository;
import com.djccnt15.spring_board.db.repository.UserStateRepository;
import com.djccnt15.spring_board.domain.user.converter.UserStateConverter;
import com.djccnt15.spring_board.domain.user.model.DetailForm;
import com.djccnt15.spring_board.enums.UserStateEnum;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminUserService {
    
    private final UserRepository userRepository;
    private final StateRepository stateRepository;
    private final UserStateConverter userStateConverter;
    private final UserStateRepository userStateRepository;
    
    public void grantManager(Long id) {
        var userEntity = userRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("can't find user"));
        userEntity.setRole(UserRoleEnum.STAFF);
        userRepository.save(userEntity);
    }
    
    public void revokeManager(Long id) {
        var userEntity = userRepository.findById(id)
            .orElseThrow(() -> new DataNotFoundException("can't find user"));
        userEntity.setRole(null);
        userRepository.save(userEntity);
    }
    
    public StateEntity getState(UserStateEnum stateEnum) {
        return stateRepository.findById(stateEnum.getId())
            .orElseThrow(() -> new DataNotFoundException("can't fine state"));
    }
    
    public void createUserState(
        DetailForm form,
        UserEntity user,
        StateEntity state
    ) {
        var userState = userStateConverter.toEntity(state, user, form.getDetail());
        userStateRepository.save(userState);
    }
    
    public void deleteUserState(
        UserEntity user,
        StateEntity state
    ) {
        var userState = userStateConverter.toEntity(state, user);
        userStateRepository.delete(userState);
    }
}
