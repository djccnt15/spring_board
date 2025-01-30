package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.dto.CommentProjection;
import com.djccnt15.spring_board.db.dto.UserCommentProjection;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.board.model.CommentResponse;
import com.djccnt15.spring_board.domain.user.model.UserCommentResponse;
import com.djccnt15.spring_board.domain.user.model.UserItemResponse;

@Converter
public class CommentConverter {
    
    public CommentEntity toEntity(
        UserEntity user,
        PostEntity post
    ) {
        return CommentEntity.builder()
            .author(user)
            .post(post)
            .build();
    }
    
    public CommentResponse toResponse(CommentProjection comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .createdDateTime(comment.getCreatedDatetime())
            .updatedDateTime(comment.getUpdatedDatetime())
            .author(comment.getUsername())
            .authorId(comment.getUserId())
            .content(comment.getContent())
            .version(comment.getVersion())
            .voteCount(comment.getVoteCount())
            .build();
    }
    
    public UserItemResponse toResponse(UserCommentProjection comment) {
        return UserCommentResponse.builder()
            .id(comment.getId())
            .category(comment.getCategory())
            .createdDateTime(comment.getCreatedDatetime())
            .updatedDateTime(comment.getUpdatedDatetime())
            .content(comment.getContent())
            .version(comment.getVersion())
            .voteCount(comment.getVoteCount())
            .postId(comment.getPostId())
            .build();
    }
}
