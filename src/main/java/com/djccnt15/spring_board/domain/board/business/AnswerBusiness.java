package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.converter.AnswerConverter;
import com.djccnt15.spring_board.domain.board.model.AnswerForm;
import com.djccnt15.spring_board.domain.board.model.AnswerResponse;
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
    private final AnswerConverter converter;
    
    public void create(
        Long id,
        AnswerForm form,
        Principal principal
    ) {
        var question = questionService.getDetail(id);
        var user = userService.getUser(principal.getName());
        answerService.create(question, form, user);
    }
    
    public void updateView(
        AnswerForm form,
        Long id,
        Principal principal
    ) {
        var entity = answerService.getAnswer(id);
        answerService.validateAuthor(entity, principal);
        form.setContent(entity.getContent());
    }
    
    public AnswerResponse update(
        Long id,
        AnswerForm form,
        Principal principal
    ) {
        var entity = answerService.getAnswer(id);
        answerService.validateAuthor(entity, principal);
        var updatedEntity = answerService.update(entity, form);
        return converter.toResponse(updatedEntity);
    }
    
    public Long delete(
        Long id,
        Principal principal
    ) {
        var entity = answerService.getAnswer(id);
        answerService.validateAuthor(entity, principal);
        answerService.delete(entity);
        return entity.getQuestionEntity().getId();
    }
    
    public Long vote(
        Long id,
        Principal principal
    ) {
        var answer = answerService.getAnswer(id);
        var user = userService.getUser(principal.getName());
        answerService.vote(answer, user);
        return answer.getQuestionEntity().getId();
    }
}
