package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import com.djccnt15.spring_board.domain.user.model.UserCreateForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController {
    
    private final UserBusiness business;
    
    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "signup_form";
    }
    
    @PostMapping(path = "/signup")
    public String signup(
        @Valid UserCreateForm form,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if (!form.getPassword1().equals(form.getPassword2())) {
            bindingResult.rejectValue(
                "password2",
                "passwordInCorrect",
                "2개의 패스워드가 일치하지 않습니다."
            );
            return "signup_form";
        }
        
        try {
            business.create(form);
        } catch (DataIntegrityViolationException e) {
            log.error("", e);  // must input throwable as a second argument for stack tracing
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "signup_form";
        } catch (Exception e) {
            log.error("", e);  // must input throwable as a second argument for stack tracing
            bindingResult.reject("signupFailed", e.getMessage());
            return "signup_form";
        }
        
        return "redirect:/";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login_form";
    }
}
