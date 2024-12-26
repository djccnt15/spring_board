package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.domain.admin.model.AdminCategoryResponse;
import com.djccnt15.spring_board.domain.admin.model.ManagerRoleRequest;
import com.djccnt15.spring_board.domain.admin.service.AdminService;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import com.djccnt15.spring_board.domain.user.model.UserResponse;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

@Slf4j
@Business
@AllArgsConstructor
public class AdminBusiness {
    
    private final UserService userService;
    private final AdminService adminService;
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;
    
    public UserRoleEnum getUserRole(UserSession user) {
        return user.getRole();
    }
    
    public Page<UserResponse> getUserList(int page) {
        return userService.getList(page);
    }
    
    public void manageAuthority(ManagerRoleRequest request) {
        switch (request.getAction()) {
            case GRANT -> adminService.grantManager(request.getId());
            case REVOKE -> adminService.revokeManager(request.getId());
        }
    }
    
    public AdminCategoryResponse getCategories(Long mainId) {
        var mainList = categoryService.getCategoryByTier(1);
        var main = mainList.stream()
            .filter(it -> it.getId().equals(mainId)).findFirst()
            .orElse(mainList.get(0));
        var subList = categoryService.getCategoryByMain(main);
        
        return AdminCategoryResponse.builder()
            .mainList(mainList.stream().map(categoryConverter::toResponse).toList())
            .subList(subList.stream().map(categoryConverter::toResponse).toList())
            .build();
    }
}
