package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.domain.board.converter.AnswerConverter;
import com.djccnt15.spring_board.domain.board.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.board.model.QuestionForm;
import com.djccnt15.spring_board.domain.board.model.QuestionResponse;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
    
    public void validateAuthor(
        QuestionEntity entity,
        Principal principal
    ) {
        if (!entity.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없습니다.");
        }
    }
    
    public void updateView(
        QuestionForm form,
        Long id,
        Principal principal
    ) {
        var entity = questionService.getDetail(id);
        validateAuthor(entity, principal);
        form.setSubject(entity.getSubject());
        form.setContent(entity.getContent());
    }
    
    public void update(
        QuestionForm form,
        Long id,
        Principal principal
    ) {
        var entity = questionService.getDetail(id);
        validateAuthor(entity, principal);
        questionService.update(entity, form);
    }
}
