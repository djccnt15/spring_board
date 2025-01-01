package com.djccnt15.spring_board.domain.qna.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.QuestionEntity;
import com.djccnt15.spring_board.domain.qna.model.QuestionResponse;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class QuestionConverter {
    
    private final UserConverter userConverter;

    public QuestionResponse toResponse(QuestionEntity entity) {
        return QuestionResponse.builder()
            .id(entity.getId())
            .subject(entity.getSubject())
            .content(entity.getContent())
            .createDateTime(entity.getCreateDateTime())
            .updateDateTime(entity.getUpdateDateTime())
            .author(userConverter.toResponse(entity.getAuthor()))
            .build();
    }
}
