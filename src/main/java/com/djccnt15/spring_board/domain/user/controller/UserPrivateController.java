package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
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
    @GetMapping("/resign")
    public String resign() {
        return "resign-form";
    }
    
    /**
     * resign user
     * @param user inject user session data from spring security
     * @return redirect to root page
     */
    @DeleteMapping("/resign")
    public String resign(@AuthenticationPrincipal UserSession user) {
        business.resign(user);
        return "redirect:/";
    }
}
