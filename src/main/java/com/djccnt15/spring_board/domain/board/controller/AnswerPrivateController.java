package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.AnswerBusiness;
import com.djccnt15.spring_board.domain.board.business.QuestionBusiness;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Slf4j
@Controller
@RequestMapping(path = "/answer")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class AnswerPrivateController {
    
    private final AnswerBusiness answerBusiness;
    private final QuestionBusiness questionBusiness;
    
    /**
     * view controller for comment registration
     * @param model injection of spring model
     * @param id question id
     * @param form Form for validation
     * @param bindingResult validated result. this must come right after the form
     * @param principal Current user injection from spring-security
     * @return redirect to question detail page
     */
    @PostMapping(path = "/{id}")
    public String create(
        Model model,
        @PathVariable("id") Long id,
        @Valid AnswerForm form,
        BindingResult bindingResult,
        Principal principal
    ) {
        var question = questionBusiness.getDetail(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        answerBusiness.create(id, form, principal);
        return String.format("redirect:/question/%s", id);
    }
}
