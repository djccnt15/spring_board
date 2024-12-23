package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.domain.admin.business.AdminBusiness;
import com.djccnt15.spring_board.domain.admin.model.ManagerRoleRequest;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(path = "/admin")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class AdminPrivateController {
    
    private final AdminBusiness business;
    
    /**
     * redirect to detail admin page
     * @param model inject from spring
     * @param user inject user session data
     * @return redirection
     */
    @GetMapping
    public String admin(
        Model model,
        @AuthenticationPrincipal UserSession user
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var userRole = business.getUserRole(user);
        if (userRole.equals(UserRoleEnum.ADMIN)) {
            return "redirect:/admin/category";
        }
        return "redirect:/admin/user";
    }
    
    /**
     * user admin page controller
     * @param model inject from spring
     * @return user admin page view
     */
    @GetMapping(path = "/user")
    public String manageUser(Model model) {
        model.addAttribute("showAdminLeftNav", true);
        var userList = business.getUserList();
        model.addAttribute("userList", userList);
        return "admin-user";
    }
    
    /**
     * grant/revoke manager role of user
     * @param request ManagerRoleRequest
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/user/role")
    public String manageAuthority(ManagerRoleRequest request) {
        business.manageAuthority(request);
        return "redirect:/admin/user";
    }
}
