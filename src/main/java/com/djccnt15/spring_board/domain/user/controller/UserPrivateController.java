package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import com.djccnt15.spring_board.domain.user.model.UserUpdateForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/user")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class UserPrivateController {
    
    private final UserBusiness business;
    
    /**
     * view controller for resign page
     * @return resign page view
     */
    @GetMapping(path = "/resign")
    public String resign() {
        return "resign-form";
    }
    
    /**
     * resign user
     * @param user inject user session data from spring security
     * @return redirect to root page
     */
    @DeleteMapping(path = "/resign")
    public String resign(@AuthenticationPrincipal UserSession user) {
        business.resign(user);
        return "redirect:/";
    }
    
    /**
     * view controller for user profile page
     * @param model inject from spring
     * @param user user session
     * @return user profile page
     */
    @GetMapping(path = "/profile")
    public String getProfile(
        Model model,
        @AuthenticationPrincipal UserSession user
    ) {
        var placeholder = business.getUserProfile(user);
        model.addAttribute("placeholder", placeholder);
        model.addAttribute("form", new UserUpdateForm());
        return "user-profile";
    }
}
