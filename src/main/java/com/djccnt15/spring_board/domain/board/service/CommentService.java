package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.CommentContentRepository;
import com.djccnt15.spring_board.db.repository.CommentRepository;
import com.djccnt15.spring_board.domain.board.converter.CommentContentConverter;
import com.djccnt15.spring_board.domain.board.converter.CommentConverter;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {
    
    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final CommentContentRepository commentContentRepository;
    private final CommentContentConverter commentContentConverter;
    
    public CommentEntity createComment(
        UserEntity user,
        PostEntity post
    ) {
        var entity = commentConverter.toEntity(user, post);
        return commentRepository.save(entity);
    }
    
    public void createCommentContent(
        CommentEntity comment,
        CommentCreateRequest request
    ) {
        var entity = commentContentConverter.toEntity(request, comment);
        commentContentRepository.save(entity);
    }
}
