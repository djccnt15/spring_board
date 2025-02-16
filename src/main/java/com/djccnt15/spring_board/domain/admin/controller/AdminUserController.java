package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminUserBusiness;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * grant manager role of user
     * @param user user session
     * @param id user id
     * @param page number of page
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/{id}/role/grant")
    public String grantAuthority(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        business.grantAuthority(user, id);
        return "redirect:/admin/user?page=%s".formatted(page);
    }
    
    /**
     * revoke manager role of user
     * @param user user session
     * @param id user id
     * @param page number of page
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/{id}/role/revoke")
    public String revokeAuthority(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        business.revokeAuthority(user, id);
        return "redirect:/admin/user?page=%s".formatted(page);
    }
    
    /**
     * unban user
     * @param user user session
     * @param id user id
     * @param page number of page
     * @return redirect to admin/ user page for refresh
     */
    @PutMapping(path = "/{id}/unban")
    public String unban(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        business.unbanUser(user, id);
        return "redirect:/admin/user?page=%s".formatted(page);
    }
}
