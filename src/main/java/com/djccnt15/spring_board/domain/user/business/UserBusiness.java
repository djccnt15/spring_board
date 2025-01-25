package com.djccnt15.spring_board.domain.user.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import com.djccnt15.spring_board.domain.user.model.UserProfile;
import com.djccnt15.spring_board.domain.user.model.UserUpdateForm;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {
    
    private final UserService service;
    private final UserConverter converter;
    
    public void createUser(UserCreateForm form) {
        service.createUser(form);
    }
    
    public void resign(UserSession user) {
        var userEntity = service.getUser(user);
        service.resign(userEntity);
    }
    
    public UserProfile getUserProfile(UserSession user) {
        var entity = service.getUser(user);
        return converter.toProfile(entity);
    }
    
    public void updateProfile(
        UserSession user,
        UserUpdateForm form
    ) {
        var userEntity = service.validateUpdateForm(user, form);
        service.updateUser(userEntity, form);
    }
}
