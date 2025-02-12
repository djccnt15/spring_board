package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import com.djccnt15.spring_board.domain.user.model.UserUpdateForm;
import com.djccnt15.spring_board.exception.FormValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(path = "/user")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class UserPrivateController {
    
    private final UserBusiness business;
    
    /**
     * view controller for resign page
     * @param model inject from spring
     * @return resign page view
     */
    @GetMapping(path = "/resign")
    public String resign(Model model) {
        model.addAttribute("showProfileLeftNav", true);
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
        return "redirect:/user/logout";
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
        model.addAttribute("showProfileLeftNav", true);
        var placeholder = business.getUserInfo(user);
        model.addAttribute("placeholder", placeholder);
        model.addAttribute("form", new UserUpdateForm());
        return "user-profile";
    }
    
    /**
     * controller for update user profile
     * @param model inject from spring
     * @param user user session
     * @param form user create form data
     * @param bindingResult validated result of form data
     * @return user profile page
     */
    @PutMapping(path = "/profile")
    public String updateProfile(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @Valid @ModelAttribute(name = "form") UserUpdateForm form,
        BindingResult bindingResult
    ) {
        model.addAttribute("showProfileLeftNav", true);
        if (bindingResult.hasErrors()) {
            model.addAttribute("placeholder", business.getUserInfo(user));
            return "user-profile";
        }
        try {
            business.updateProfile(user, form);
        } catch (FormValidationException e) {
            bindingResult.reject("updateFailed", e.getMessage());
            model.addAttribute("placeholder", business.getUserInfo(user));
            return "user-profile";
        }
        model.addAttribute("placeholder", business.getUserInfo(user));
        return "user-profile";
    }
    
    /**
     * view controller for user post list page
     * @param model inject from spring
     * @param user user session
     * @param size size of post list
     * @param page number of page
     * @return user post list view
     */
    @GetMapping(path = "/profile/post-list")
    public String getPostList(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        model.addAttribute("showProfileLeftNav", true);
        var response = business.getUserPost(user, size, page);
        model.addAttribute("response", response);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "user-profile-post-list";
    }
    
    /**
     * view controller for user post list page
     * @param model inject from spring
     * @param user user session
     * @param size size of post list
     * @param page number of page
     * @return user post list view
     */
    @GetMapping(path = "/profile/comment-list")
    public String getCommentList(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        model.addAttribute("showProfileLeftNav", true);
        var response = business.getUserComment(user, size, page);
        model.addAttribute("response", response);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "user-profile-comment-list";
    }
}
