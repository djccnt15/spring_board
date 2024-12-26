package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
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
@RequestMapping(path = "/admin")
@RequiredArgsConstructor
public class AdminPrivateController {
    
    private final AdminUserBusiness business;
    
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
}
