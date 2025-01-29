package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.dto.PostDetailProjection;
import com.djccnt15.spring_board.db.dto.PostMinimalProjection;
import com.djccnt15.spring_board.db.dto.UserPostProjection;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.board.model.PostDetailResponse;
import com.djccnt15.spring_board.domain.board.model.PostMinimalResponse;
import com.djccnt15.spring_board.domain.user.model.UserItemResponse;
import com.djccnt15.spring_board.domain.user.model.UserPostResponse;

@Converter
public class PostConverter {
    
    public PostEntity toEntity(
        UserEntity user,
        CategoryEntity category
    ) {
        return PostEntity.builder()
            .category(category)
            .author(user)
            .build();
    }
    
    public PostDetailResponse toResponse(PostDetailProjection post) {
        return PostDetailResponse.builder()
            .id(post.getId())
            .createdDateTime(post.getCreatedDatetime())
            .updatedDateTime(post.getUpdatedDatetime())
            .author(post.getUsername())
            .authorId(post.getUserId())
            .category(post.getCategory())
            .title(post.getTitle())
            .content(post.getContent())
            .version(post.getVersion())
            .viewCount(post.getViewCount())
            .commentCount(post.getCommentCount())
            .voteCount(post.getVoteCount())
            .build();
    }
    
    public PostMinimalResponse toResponse(PostMinimalProjection post) {
        return PostMinimalResponse.builder()
            .id(post.getId())
            .createdDateTime(post.getCreatedDatetime())
            .title(post.getTitle())
            .build();
    }
    
    public UserItemResponse toResponse(UserPostProjection post) {
        return UserPostResponse.builder()
            .id(post.getId())
            .createdDateTime(post.getCreatedDatetime())
            .updatedDateTime(post.getUpdatedDatetime())
            .mainCategory(post.getMainCategory())
            .subCategory(post.getSubCategory())
            .title(post.getTitle())
            .content(post.getContent())
            .viewCount(post.getViewCount())
            .commentCount(post.getCommentCount())
            .voteCount(post.getVoteCount())
            .build();
    }
}
