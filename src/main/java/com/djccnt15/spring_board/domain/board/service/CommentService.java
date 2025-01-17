package com.djccnt15.spring_board.domain.board.service;

import com.djccnt15.spring_board.db.entity.CommentContentEntity;
import com.djccnt15.spring_board.db.entity.CommentEntity;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.db.entity.UserEntity;
import com.djccnt15.spring_board.db.repository.CommentContentRepository;
import com.djccnt15.spring_board.db.repository.CommentRepository;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.converter.CommentContentConverter;
import com.djccnt15.spring_board.domain.board.converter.CommentConverter;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import com.djccnt15.spring_board.domain.board.model.CommentResponse;
import com.djccnt15.spring_board.domain.board.model.PostDetailResponse;
import com.djccnt15.spring_board.exception.DataNotFoundException;
import com.djccnt15.spring_board.exception.InvalidAuthorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    public List<CommentResponse> getCommentList(PostDetailResponse post) {
        var commentList = commentRepository.getCommentListByPostId(post.getId());
        return commentList.stream()
            .map(commentConverter::toResponse)
            .toList();
    }
    
    public CommentEntity getComment(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
            () -> new DataNotFoundException("can't find requested comment")
        );
    }
    
    public void validateAuthor(
        UserSession user,
        CommentEntity comment
    ) {
        if (!user.getUserId().equals(comment.getAuthor().getId())) {
            throw new InvalidAuthorException("invalid author exception");
        }
    }
    
    public CommentContentEntity getLastCommentContent(CommentEntity comment) {
        return commentContentRepository.findFirstByCommentIdOrderByIdDesc(comment.getId()).orElseThrow(
            () -> new DataNotFoundException("can't find requested comment")
        );
    }
    
    public void updateCommentContent(
        CommentEntity comment,
        CommentContentEntity commentContent,
        CommentCreateRequest request
    ) {
        var entity = commentContentConverter.toEntity(comment, commentContent, request);
        commentContentRepository.save(entity);
    }
}
