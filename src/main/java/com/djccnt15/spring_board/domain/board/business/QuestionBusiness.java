package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.converter.AnswerConverter;
import com.djccnt15.spring_board.domain.board.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import com.djccnt15.spring_board.domain.board.model.QuestionResponse;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.security.Principal;

@Slf4j
@Business
@RequiredArgsConstructor
public class QuestionBusiness {
    
    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final AnswerConverter answerConverter;
    private final UserService userService;
    
    public Page<QuestionResponse> getList(int page) {
        return questionService.getList(page);
    }
    
    public QuestionResponse getDetail(Long id) {
        var questionEntity = questionService.getDetail(id);
        var answerList = questionEntity.getAnswerEntityList().stream()
            .map(answerConverter::toResponse)
            .toList();
        var question = questionConverter.toResponse(questionEntity);
        question.setAnswerList(answerList);
        return question;
    }
    
    public void create(
        QuestionForm form,
        Principal principal
    ) {
        var user = userService.getUser(principal.getName());
        questionService.create(form, user);
    }
}
