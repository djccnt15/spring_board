package com.djccnt15.spring_board.domain.user.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.dto.UserCommentProjection;
import com.djccnt15.spring_board.db.dto.UserPostProjection;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.user.model.UserCommentResponse;
import com.djccnt15.spring_board.domain.user.model.UserPostResponse;
import com.djccnt15.spring_board.domain.user.model.UserResponse;

@Converter
public class UserConverter {
    
    public UserResponse toResponse(UserEntity entity) {
        return UserResponse.builder()
            .id(entity.getId())
            .username(entity.getUsername())
            .email(entity.getEmail())
            .createDateTime(entity.getCreatedDatetime())
            .role(entity.getRole())
            .isVerified(entity.isVerified())
            .isBanned(entity.isBanned())
            .isLocked(entity.isLocked())
            .build();
    }
    
    public UserPostResponse toResponse(UserPostProjection post) {
        return UserPostResponse.builder()
            .id(post.getId())
            .createdDateTime(post.getCreatedDatetime())
            .updatedDateTime(post.getUpdatedDatetime())
            .mainCategory(post.getMainCategory())
            .subCategory(post.getSubCategory())
            .title(post.getTitle())
            .content(post.getContent())
            .version(post.getVersion())
            .viewCount(post.getViewCount())
            .commentCount(post.getCommentCount())
            .voteCount(post.getVoteCount())
            .build();
    }
    
    public UserCommentResponse toResponse(UserCommentProjection comment) {
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
