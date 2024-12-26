package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminUserBusiness;
import com.djccnt15.spring_board.domain.admin.model.ManagerRoleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping(path = "/admin/user")
@RequiredArgsConstructor
public class AdminUserController {
    
    private final AdminUserBusiness business;
    
    /**
     * user admin page controller
     * @param model inject from spring
     * @param page selected page from client
     * @return user admin page view
     */
    @GetMapping
    public String manageUser(
        Model model,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var userList = business.getUserList(page);
        model.addAttribute("userList", userList);
        return "admin-user";
    }
    
    /**
     * grant/revoke manager role of user
     * @param request ManagerRoleRequest
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/role")
    public String manageAuthority(
        ManagerRoleRequest request,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        business.manageAuthority(request);
        return "redirect:/admin/user?page=%s".formatted(page);
    }
}
