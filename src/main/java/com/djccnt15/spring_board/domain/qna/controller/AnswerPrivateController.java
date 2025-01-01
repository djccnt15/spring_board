package com.djccnt15.spring_board.domain.qna.controller;

import com.djccnt15.spring_board.domain.qna.business.AnswerBusiness;
import com.djccnt15.spring_board.domain.qna.business.QuestionBusiness;
import com.djccnt15.spring_board.domain.qna.model.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
        @PathVariable(value = "id") Long id,
        @Valid AnswerForm form,
        BindingResult bindingResult,
        Principal principal
    ) {
        var question = questionBusiness.getDetail(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "question_detail";
        }
        var answer = answerBusiness.create(id, form, principal);
        return "redirect:/question/%s#answer_%s".formatted(id, answer.getId());
    }
    
    /**
     * view controller for answer update view
     * @param form Form for data model
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return redirect to answer update page
     */
    @GetMapping(path = "/update/{id}")
    public String update(
        AnswerForm form,
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        answerBusiness.updateView(form, id, principal);
        return "answer_form";
    }
    
    /**
     * view controller for answer update
     * @param form Form for data model
     * @param bindingResult validated result. this must come right after the form
     * @param id question id
     * @param principal Current user injection from spring-security
     * @return redirect to question detail view
     */
    @PostMapping(path = "/update/{id}")
    public String update(
        @Valid AnswerForm form,
        BindingResult bindingResult,
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        var answer = answerBusiness.update(id, form, principal);
        form.setContent(answer.getContent());
        return "redirect:/question/%s#answer_%s".formatted(answer.getQuestion().getId(), answer.getId());
    }
    
    /**
     * view controller for answer delete function
     * @param id answer id
     * @param principal Current user injection from spring-security
     * @return redirect to question detail page
     */
    @GetMapping(path = "/delete/{id}")
    public String delete(
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        var questionId = answerBusiness.delete(id, principal);
        return "redirect:/question/%s".formatted(questionId);
    }
    
    /**
     * view controller for question vote
     * @param id answer id
     * @param principal Current user injection from spring-security
     * @return redirect to current page
     */
    @GetMapping(path = "/vote/{id}")
    public String vote(
        @PathVariable(value = "id") Long id,
        Principal principal
    ) {
        var answer = answerBusiness.vote(id, principal);
        return "redirect:/question/%s#answer_%s".formatted(answer.getQuestion().getId(), answer.getId());
    }
}
