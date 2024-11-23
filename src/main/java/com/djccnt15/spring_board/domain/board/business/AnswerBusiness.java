package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import com.djccnt15.spring_board.domain.board.service.AnswerService;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class AnswerBusiness {
    
    private final QuestionService questionService;
    private final AnswerService answerService;
    
    public void create(
        Long id,
        AnswerForm form
    ) {
        var question = questionService.getDetail(id);
        answerService.create(question, form);
    }
}
