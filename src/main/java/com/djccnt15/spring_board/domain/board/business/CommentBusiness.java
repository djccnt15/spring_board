package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import com.djccnt15.spring_board.domain.board.model.CommentResponse;
import com.djccnt15.spring_board.domain.board.service.CommentService;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class CommentBusiness {
    
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    
    public void createComment(
        UserSession user,
        CommentCreateRequest request,
        Long postId
    ) {
        var userEntity = userService.getUser(user.getUserId());
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
}
