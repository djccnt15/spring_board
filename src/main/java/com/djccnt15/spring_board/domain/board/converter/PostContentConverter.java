package com.djccnt15.spring_board.domain.board.converter;

import com.djccnt15.spring_board.annotations.Converter;
import com.djccnt15.spring_board.db.entity.PostContentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;

@Converter
public class PostContentConverter {
    
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
}
