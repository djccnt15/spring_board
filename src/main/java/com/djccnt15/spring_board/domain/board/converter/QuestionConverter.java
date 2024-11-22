package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.domain.board.model.QuestionResponse;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class QuestionConverter {

    public QuestionResponse toResponse(QuestionEntity entity) {
        return QuestionResponse.builder()
            .id(entity.getId())
            .subject(entity.getSubject())
            .content(entity.getContent())
            .createDateTime(entity.getCreateDateTime())
            .build();
    }
}
