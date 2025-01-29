package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.PostContentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.domain.board.model.PostContentHistory;
import com.djccnt15.spring_board.domain.board.model.PostContentResponse;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.utils.StringUtil;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class PostContentConverter {
    
    private final StringUtil stringUtil;
    
    public PostContentEntity toEntity(
        PostCreateRequest request,
        PostEntity post
    ) {
        return PostContentEntity.builder()
            .title(request.getTitle())
            .content(request.getContent())
            .post(post)
            .build();
    }
    
    public PostContentEntity toEntity(
        PostEntity post,
        PostContentEntity postContent,
        PostCreateRequest request
    ) {
        return PostContentEntity.builder()
            .version(postContent.getVersion() + 1)
            .title(request.getTitle())
            .content(request.getContent())
            .post(post)
            .build();
    }
    
    public PostContentResponse toResponse(PostContentEntity entity) {
        return PostContentResponse.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .createdDateTime(entity.getCreatedDatetime())
            .build();
    }
    
    public PostContentHistory toHistory(PostContentEntity entity) {
        var createDateTime = stringUtil.datetimeFormatter(
            entity.getCreatedDatetime(), "yyyy-MM-dd HH:mm:ss"
        );
        return PostContentHistory.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .content(entity.getContent())
            .createdDateTime(createDateTime)
            .build();
    }
}
