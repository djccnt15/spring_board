package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.converter.CommentContentConverter;
import com.djccnt15.spring_board.domain.board.model.CommentContentResponse;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import com.djccnt15.spring_board.domain.board.model.CommentResponse;
import com.djccnt15.spring_board.domain.board.service.CommentService;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import com.djccnt15.spring_board.utils.model.FileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Business
@RequiredArgsConstructor
public class CommentBusiness {
    
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final CommentContentConverter commentContentConverter;
    
    public void createComment(
        UserSession user,
        CommentCreateRequest request,
        Long postId
    ) {
        var userEntity = userService.getUser(user);
        var post = postService.getPost(postId);
        var comment = commentService.createComment(userEntity, post);
        commentService.createCommentContent(comment, request);
    }
    
    public CommentResponse getCommentUpdatePlaceholder(
        UserSession user,
        Long commentId
    ) {
        var comment = commentService.getComment(commentId);
        commentService.validateAuthor(user, comment);
        var commentContent = commentService.getLastCommentContent(comment);
        return CommentResponse.builder()
            .content(commentContent.getContent())
            .build();
    }
    
    public void updateComment(
        UserSession user,
        Long commentId,
        CommentCreateRequest request
    ) {
        var comment = commentService.getComment(commentId);
        commentService.validateAuthor(user, comment);
        var commentContent = commentService.getLastCommentContent(comment);
        commentService.updateCommentContent(comment, commentContent, request);
    }
    
    public void deleteComment(
        UserSession user,
        Long commentId
    ) {
        var comment = commentService.getComment(commentId);
        commentService.validateAuthor(user, comment);
        commentService.deleteComment(comment);
    }
    
    public void voteComment(
        UserSession user,
        Long id
    ) {
        var userEntity = userService.getUser(user);
        var comment = commentService.getComment(id);
        var isVoted = commentService.getVoted(comment, userEntity);
        isVoted.ifPresentOrElse(
            commentService::revokeVote,
            () -> commentService.voteComment(comment, userEntity)
        );
    }
    
    public List<CommentContentResponse> getCommentHistory(Long id) {
        var commentHistory = commentService.getCommentHistory(id);
        return commentHistory.stream()
            .map(commentContentConverter::toResponse)
            .toList();
    }
    
    public FileResponse createHistoryCsv(Long id) {
        var history = commentService.getCommentHistory(id);
        var commentHistory = history.stream()
            .map(commentContentConverter::toHistory)
            .toList();
        return commentService.createHistoryCsv(commentHistory);
    }
    
    public FileResponse createHistoryExcel(
        UserSession user,
        Long id
    ) {
        var entityHistory = commentService.getCommentHistory(id);
        var commentHistory = entityHistory.stream()
            .map(commentContentConverter::toHistory)
            .toList();
        return commentService.createHistoryExcel(user, commentHistory);
    }
}
