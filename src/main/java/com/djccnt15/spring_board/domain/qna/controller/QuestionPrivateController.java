package com.djccnt15.spring_board.domain.qna.controller;

import com.djccnt15.spring_board.domain.qna.business.QuestionBusiness;
import com.djccnt15.spring_board.domain.qna.model.QuestionForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping(path = "/form")
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
    
    /**
     * view controller for get question update view
     * @param form Form to input view as a model
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return question update page
     */
    @GetMapping(path = "/form/{id}")
    public String update(
        QuestionForm form,
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        business.updateView(form, id, principal);
        return "question_form";
    }
    
    /**
     * view controller for question update view
     * @param questionForm Form for data model
     * @param bindingResult validated result. this must come right after the form
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return redirect to updated question
     */
    @PostMapping(path = "/form/{id}")
    public String update(
        @Valid QuestionForm questionForm,
        BindingResult bindingResult,
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        business.update(questionForm, id, principal);
        return "redirect:/question/%s".formatted(id);
    }
    
    /**
     * view controller for question delete function
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return redirect to root page
     */
    @GetMapping(path = "/delete/{id}")
    public String delete(
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        business.delete(id, principal);
        return "redirect:/";
    }
    
    /**
     * view controller for question vote
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return redirect to current page
     */
    @GetMapping(path = "/vote/{id}")
    public String vote(
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        business.vote(id, principal);
        return "redirect:/question/%s".formatted(id);
    }
}
