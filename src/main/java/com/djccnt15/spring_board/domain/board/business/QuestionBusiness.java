package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.converter.QuestionConverter;
import com.djccnt15.spring_board.domain.board.model.QuestionResponse;
import com.djccnt15.spring_board.domain.board.service.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Business
@RequiredArgsConstructor
public class QuestionBusiness {
    
    private final QuestionService service;
    private final QuestionConverter questionConverter;
    
    public List<QuestionResponse> getList() {
        return service.getList();
    }
    
    public QuestionResponse getDetail(Long id) {
        var questionEntity = service.getDetail(id);
        var question = questionConverter.toResponse(questionEntity);
        return question;
    }
}
