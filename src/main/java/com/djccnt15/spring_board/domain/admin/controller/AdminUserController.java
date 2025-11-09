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
     * @param size size of user list
     * @param page selected page from client
     * @param kw search keyword
     * @return user admin page view
     */
    @GetMapping
    public String manageUser(
        Model model,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String kw
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var userList = business.getUserList(size, page, kw);
        model.addAttribute("userList", userList);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("kw", kw);
        return "admin-user";
    }
    
    /**
     * grant manager role of user
     * @param user user session
     * @param id user id
     * @param size size of user list
     * @param page number of page
     * @param kw search keyword
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/{id}/role/grant")
    public String grantAuthority(
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long id,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String kw,
    ) {
        business.grantAuthority(user, id);
        return "redirect:/admin/user?page=%s&size=%s&kw=%s".formatted(page, size, keyword);
    }
    
    /**
     * revoke manager role of user
     * @param user user session
     * @param id user id
     * @param size size of user list
     * @param page number of page
     * @param kw search keyword
     * @return redirect to admin/user page for refresh
     */
    @PatchMapping(path = "/{id}/role/revoke")
    public String revokeAuthority(
        @AuthenticationPrincipal UserSession user,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "kw", defaultValue = "") String keyword
        @PathVariable Long id,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String kw,
    ) {
        business.revokeAuthority(user, id);
        return "redirect:/admin/user?page=%s&size=%s&kw=%s".formatted(page, size, keyword);
    }
    
    /**
     * unban user
     * @param user user session
     * @param id user id
     * @param size size of user list
     * @param page number of page
     * @param kw search keyword
     * @return redirect to admin user page for refresh
     */
    @PutMapping(path = "/{id}/unban")
    public String unban(
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long id,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String kw,
    ) {
        business.unBanUser(user, id);
        return "redirect:/admin/user?page=%s&size=%s&kw=%s".formatted(page, size, keyword);
    }
    
    /**
     * unblock user
     * @param user user session
     * @param id user id
     * @param size size of user list
     * @param page number of page
     * @param kw search keyword
     * @return redirect to admin user page for refresh
     */
    @PutMapping(path = "/{id}/unblock")
    public String unblock(
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long id,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "") String kw,
    ) {
        business.unBlockUser(user, id);
        return "redirect:/admin/user?page=%s&size=%s&kw=%s".formatted(page, size, keyword);
    }
}
