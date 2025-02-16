package com.djccnt15.spring_board.domain.qna.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.qna.converter.AnswerConverter;
import com.djccnt15.spring_board.domain.qna.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.qna.model.QuestionForm;
import com.djccnt15.spring_board.domain.qna.model.QuestionResponse;
import com.djccnt15.spring_board.domain.qna.service.QuestionService;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.stream.Collectors;

@Slf4j
@Business
@RequiredArgsConstructor
public class QuestionBusiness {
    
    private final QuestionService questionService;
    private final QuestionConverter questionConverter;
    private final AnswerConverter answerConverter;
    private final UserService userService;
    private final UserConverter userConverter;
    
    public Page<QuestionResponse> getList(int page, String keyword) {
        return questionService.getList(page, keyword);
    }
    
    public QuestionResponse getDetail(Long id) {
        var questionEntity = questionService.getDetail(id);
        var answerList = questionEntity.getAnswerEntityList().stream()
            .map(answerConverter::toResponse)
            .toList();
        var voterList = questionEntity.getVoter().stream()
            .map(userConverter::toResponse)
            .collect(Collectors.toSet());
        var question = questionConverter.toResponse(questionEntity);
        question.setAnswerList(answerList);
        question.setVoterList(voterList);
        return question;
    }
    
    public void create(
        QuestionForm form,
        Principal principal
    ) {
        var user = userService.getUser(principal.getName());
        questionService.create(form, user);
    }
    
    public void updateView(
        QuestionForm form,
        Long id,
        Principal principal
    ) {
        var entity = questionService.getDetail(id);
        questionService.validateAuthor(entity, principal);
        form.setSubject(entity.getSubject());
        form.setContent(entity.getContent());
    }
    
    public void update(
        QuestionForm form,
        Long id,
        Principal principal
    ) {
        var entity = questionService.getDetail(id);
        questionService.validateAuthor(entity, principal);
        questionService.update(entity, form);
    }
    
    public void delete(
        Long id,
        Principal principal
    ) {
        var question = questionService.getDetail(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
        questionService.delete(question);
    }
    
    public void vote(
        Long id,
        Principal principal
    ) {
        var question = questionService.getDetail(id);
        var user = userService.getUser(principal.getName());
        questionService.vote(question, user);
    }
}
