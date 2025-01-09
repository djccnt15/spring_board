package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.dto.DetailedPostSummary;
import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.domain.board.model.DetailedPostSummaryResponse;

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
    
    public DetailedPostSummaryResponse toResponse(DetailedPostSummary post) {
        return DetailedPostSummaryResponse.builder()
            .id(post.getId())
            .createdDatetime(post.getCreatedDatetime())
            .updatedDatetime(post.getUpdatedDatetime())
            .username(post.getUsername())
            .title(post.getTitle())
            .content(post.getContent())
            .commentCount(post.getCommentCount())
            .voteCount(post.getVoteCount())
            .build();
    }
}
