package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class PostBusiness {
    
    private final UserService userService;
    private final CategoryService categoryService;
    private final PostService postService;
    
    public PostEntity createPost(
        UserSession session,
        String categoryName,
        PostCreateRequest request
    ) {
        var user = userService.getUser(session.getUserId());
        var category = categoryService.getCategory(categoryName);
        var post = postService.createPost(user, category);
        postService.createContent(post, request);
        return post;
    }
}
