package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CommentContentEntity;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.domain.board.model.CommentContentHistory;
import com.djccnt15.spring_board.domain.board.model.CommentContentResponse;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import com.djccnt15.spring_board.utils.CommonUtil;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class CommentContentConverter {
    
    private final CommonUtil commonUtil;
    
    public CommentContentEntity toEntity(
        CommentCreateRequest request,
        CommentEntity comment
    ) {
        return CommentContentEntity.builder()
            .content(request.getContent())
            .comment(comment)
            .build();
    }
    
    public CommentContentEntity toEntity(
        CommentEntity comment,
        CommentContentEntity commentContent,
        CommentCreateRequest request
    ) {
        return CommentContentEntity.builder()
            .version(commentContent.getVersion() + 1)
            .content(request.getContent())
            .comment(comment)
            .build();
    }
    
    public CommentContentResponse toResponse(CommentContentEntity entity) {
        return CommentContentResponse.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .createdDateTime(entity.getCreatedDatetime())
            .build();
    }
    
    public CommentContentHistory toHistory(CommentContentEntity entity) {
        var createDateTime = commonUtil.datetimeFormatter(
            entity.getCreatedDatetime(), "yyyy-MM-dd HH:mm:ss"
        );
        return CommentContentHistory.builder()
            .id(entity.getId())
            .content(entity.getContent())
            .createdDateTime(createDateTime)
            .build();
    }
}
