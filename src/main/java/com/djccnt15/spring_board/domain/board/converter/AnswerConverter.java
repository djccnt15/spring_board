package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.domain.board.model.AnswerResponse;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class AnswerConverter {
    
    private final UserConverter userConverter;
    private final QuestionConverter questionConverter;
    
    public AnswerResponse toResponse(AnswerEntity entity) {
        return AnswerResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .createDateTime(entity.getCreateDateTime())
            .updateDateTime(entity.getUpdateDateTime())
            .author(userConverter.toResponse(entity.getAuthor()))
            .question(questionConverter.toResponse(entity.getQuestionEntity()))
            .build();
    }
}
