package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.admin.service.AdminUserService;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.model.DetailForm;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.domain.user.service.UserService;
import com.djccnt15.spring_board.enums.UserStateEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
@Business
@RequiredArgsConstructor
public class AdminUserBusiness {
    
    private final UserService userService;
    private final UserConverter userConverter;
    private final AdminUserService adminUserService;
    
    public Page<UserResponse> getUserList(
        int size,
        int page,
        String keyword
    ) {
        var kw = "%%%s%%".formatted(keyword);
        var entityList = userService.getList(size, page, kw);
        entityList.map(userService::setState);
        return entityList.map(userConverter::toResponse);
    }
    
    public void grantAuthority(
        UserSession user,
        Long id
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not manager");
        adminUserService.grantManager(id);
    }
    
    public void revokeAuthority(
        UserSession user,
        Long id
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not manager");
        adminUserService.revokeManager(id);
    }
    
    public void banUser(
        UserSession user,
        Long id,
        DetailForm form
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not a manager");
        var targetUser = userService.getUser(id);
        var state = adminUserService.getState(UserStateEnum.BANNED);
        adminUserService.createUserState(form, targetUser, state);
    }
    
    public void unBanUser(
        UserSession user,
        Long id
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not a manager");
        var targetUser = userService.getUser(id);
        var state = adminUserService.getState(UserStateEnum.BANNED);
        adminUserService.deleteUserState(targetUser, state);
    }
    
    public void blockUser(
        UserSession user,
        Long id,
        DetailForm form
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not a manager");
        var targetUser = userService.getUser(id);
        var state = adminUserService.getState(UserStateEnum.LOCKED);
        adminUserService.createUserState(form, targetUser, state);
    }
    
    public void unBlockUser(
        UserSession user,
        Long id
    ) {
        var entity = userService.getUser(user);
        var validation = userService.validateManager(entity);
        if (!validation) throw new RuntimeException("you are not a manager");
        var targetUser = userService.getUser(id);
        var state = adminUserService.getState(UserStateEnum.LOCKED);
        adminUserService.deleteUserState(targetUser, state);
    }
}
