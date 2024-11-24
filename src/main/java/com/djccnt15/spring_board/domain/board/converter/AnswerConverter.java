package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.domain.board.model.AnswerResponse;

@Converter
public class AnswerConverter {
    
    public AnswerResponse toResponse(AnswerEntity entity) {
        return AnswerResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .createDateTime(entity.getCreateDateTime())
            .build();
    }
}
