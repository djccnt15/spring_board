package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.domain.admin.model.ManagerRoleRequest;
import com.djccnt15.spring_board.domain.admin.service.AdminUserService;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
@Business
@RequiredArgsConstructor
public class AdminUserBusiness {
    
    private final UserService userService;
    private final AdminUserService adminUserService;
    
    public UserRoleEnum getUserRole(UserSession user) {
        return user.getRole();
    }
    
    public Page<UserResponse> getUserList(int page) {
        return userService.getList(page);
    }
    
    public void manageAuthority(ManagerRoleRequest request) {
        switch (request.getAction()) {
            case GRANT -> adminUserService.grantManager(request.getId());
            case REVOKE -> adminUserService.revokeManager(request.getId());
        }
    }
}
