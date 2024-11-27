package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import com.djccnt15.spring_board.domain.board.service.AnswerService;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.Principal;

@Slf4j
@Business
@RequiredArgsConstructor
public class AnswerBusiness {
    
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final UserService userService;
    
    public void create(
        Long id,
        AnswerForm form,
        Principal principal
    ) {
        var question = questionService.getDetail(id);
        var user = userService.getUser(principal.getName());
        answerService.create(question, form, user);
    }
}
