package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.CommentContentEntity;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;

@Converter
public class CommentContentConverter {
    
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
}
