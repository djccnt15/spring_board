package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import com.djccnt15.spring_board.domain.user.model.UserRecoveryForm;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.FormValidationException;
import com.djccnt15.spring_board.exception.UserVerifyException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserBusiness business;
    
    /**
     * view controller to signup form
     * @param userCreateForm user create form inject for view
     * @return signup page
     */
    @GetMapping(path = "/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup-form";
    }
    
    /**
     * view controller for User signup
     * @param request HttpServletRequest to get url data
     * @param form user create form data
     * @param bindingResult validated result of form data
     * @return user verify page
     */
    @PostMapping(path = "/signup")
    public String signup(
        HttpServletRequest request,
        @Valid UserCreateForm form,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signup-form";
        }
        if (!form.getPassword1().equals(form.getPassword2())) {
            bindingResult.rejectValue(
                "password2",
                "passwordInCorrect",
                "2개의 패스워드가 일치하지 않습니다."
            );
            return "signup-form";
        }
        
        try {
            business.createUser(form, request);
        } catch (DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup-form";
        } catch (Exception e) {
            log.error("", e);  // must input throwable as a second argument for stack tracing
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup-form";
        }
        
        return "user-verify";
    }
    
    /**
     * view controller for login
     * @return login page
     */
    @GetMapping(path = "/login")
    public String login() {
        return "login-form";
    }
    
    /**
     * view controller for user recovery
     * @param model inject from spring
     * @return user password recovery page
     */
    @GetMapping(path = "/recovery")
    public String recoveryView(Model model) {
        model.addAttribute("form", new UserRecoveryForm());
        return "user-recovery";
    }
    
    /**
     * controller for user recovery business logic
     * @param form actual data for user recovery
     * @param bindingResult validated result of the form. this must come right after the form
     * @return redirect to login page
     */
    @PostMapping(path = "/recovery")
    public String recoverUser(
        @Valid @ModelAttribute(name = "form") UserRecoveryForm form,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "user-recovery";
        }
        try {
            business.recoverUser(form);
        } catch (DataNotFoundException e) {
            bindingResult.reject("userNotFound", e.getMessage());
            return "user-recovery";
        } catch (FormValidationException e) {
            bindingResult.reject("validationFailed", e.getMessage());
            return "user-recovery";
        }
        return "user-recovered";
    }
    
    /**
     * view controller for session expired
     * @return session expired page
     */
    @GetMapping(path = "/session-expired")
    public String sessionExpired() {
        return "session-expired";
    }
    
    /**
     * view controller for user verification
     * @param id user id
     * @param key user secret key
     * @return user verified page
     */
    @PostMapping(path = "/verify/{id}")
    public String verifyUser(
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "key") String key
    ) {
        try {
            business.verifyUser(id, key);
        } catch (UserVerifyException e) {
            return "redirect:/user/verify/%s?error=%s".formatted(
                id, URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)
            );
        }
        return "user-verified";
    }
    
    /**
     * view controller for user verification
     * @param model inject from spring
     * @param id user id
     * @return user verified page
     */
    @GetMapping(path = "/verify/{id}")
    public String verifyUser(
        Model model,
        @PathVariable(value = "id") Long id
    ) {
        model.addAttribute("id", id);
        return "user-verified";
    }
    
    /**
     * view controller for retrying user verify
     * @param request HttpServletRequest to get
     * @param id user id
     * @return user verify page
     */
    @GetMapping(path = "/verify/{id}/retry")
    public String retryVerify(
        HttpServletRequest request,
        @PathVariable(value = "id") Long id
    ) {
        business.retryVerify(id, request);
        return "user-verify";
    }
}
