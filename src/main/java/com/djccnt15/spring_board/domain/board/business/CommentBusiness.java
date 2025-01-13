package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
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
        UserSession session,
        CommentCreateRequest request,
        Long postId
    ) {
        var user = userService.getUser(session.getUserId());
        var post = postService.getPost(postId);
        var comment = commentService.createComment(user, post);
        commentService.createCommentContent(comment, request);
    }
}
