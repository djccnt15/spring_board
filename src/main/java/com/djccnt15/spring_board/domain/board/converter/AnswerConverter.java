package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.AnswerEntity;
import com.djccnt15.spring_board.domain.board.model.AnswerResponse;
import com.djccnt15.spring_board.domain.user.converter.UserConverter;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
@RequiredArgsConstructor
public class AnswerConverter {
    
    private final UserConverter userConverter;
    private final QuestionConverter questionConverter;
    
    public AnswerResponse toResponse(AnswerEntity entity) {
        var voterList = Optional.ofNullable(entity.getVoter())
            .orElseGet(Collections::emptySet)
            .stream()
            .map(userConverter::toResponse)
            .collect(Collectors.toSet());
        return AnswerResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .createDateTime(entity.getCreateDateTime())
            .updateDateTime(entity.getUpdateDateTime())
            .author(userConverter.toResponse(entity.getAuthor()))
            .question(questionConverter.toResponse(entity.getQuestionEntity()))
            .voterList(voterList)
            .build();
    }
}
