package com.djccnt15.spring_board.domain.user.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Business
@RequiredArgsConstructor
public class UserBusiness {
    
    private final UserService service;
    
    public void create(UserCreateForm form) {
        service.create(form);
    }
    
    public void resign(UserSession user) {
        var userEntity = service.getUser(user.getUserId());
        service.resign(userEntity);
    }
}
