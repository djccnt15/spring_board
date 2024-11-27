package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.QuestionBusiness;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping(path = "/question")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class QuestionPrivateController {
    
    private final QuestionBusiness business;
    
    /**
     * view controller for question registration
     * @param form Form for validation
     * @param bindingResult validated result. this must come right after the form
     * @param principal Current user injection from spring-security
     * @return redirect to question list page
     */
    @PostMapping(path = "form")
    public String create(
        @Valid QuestionForm form,
        BindingResult bindingResult,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        business.create(form, principal);
        return "redirect:/question";
    }
}
