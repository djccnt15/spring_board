package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.QuestionBusiness;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping(path = "/question")
@RequiredArgsConstructor
public class QuestionController {
    
    private final QuestionBusiness business;
    
    /**
     * view controller for question list
     * @param model injection of spring model
     * @return question list view
     */
    @GetMapping
    public String list(Model model) {
        var questionList = business.getList();
        model.addAttribute("questionList", questionList);
        return "question_list";
    }
    
    /**
     * view controller for question detail
     * @param model injection of spring model
     * @param id question id
     * @param form this is only for avoiding error of using `th:object="${answerForm}"`
     * @return question detail view
     */
    @GetMapping(path = "/{id}")
    public String detail(
        Model model,
        @PathVariable Long id,
        AnswerForm form
    ) {
        var question = business.getDetail(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
}
