package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;
import java.util.List;

@Slf4j
@Business
@AllArgsConstructor
public class AdminBusiness {
    
    private final UserService userService;
    
    public UserRoleEnum getUserRole(Principal principal) {
        return userService.getUser(principal.getName()).getRole();
    }
    
    public List<UserResponse> getUserList() {
        return userService.getUserList();
    }
}
