package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.domain.board.model.PostListResponse;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import com.djccnt15.spring_board.domain.board.model.PostSummaryListResponse;
import com.djccnt15.spring_board.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Business
@RequiredArgsConstructor
public class PostBusiness {
    
    private final UserService userService;
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;
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
    
    public PostListResponse getPostList(
        String categoryName,
        int size,
        int page,
        String keyword
    ) {
        var category = categoryService.getCategory(categoryName);
        var kw = "%%%s%%".formatted(keyword);
        var postList = postService.getPostList(category, size, page, kw);
        var postListCount = postService.getPostListCount(category, kw);
        var totalPageCount = (int) Math.ceil((double) postListCount / size);
        return PostListResponse.builder()
            .totalPages(totalPageCount)
            .postList(postList)
            .build();
    }
    
    public List<PostSummaryListResponse> getIndexPostList() {
        var mainCategoryList = categoryService.getCategoryByTier(1);
        return mainCategoryList.stream()
            .map(it -> PostSummaryListResponse.builder()
                .category(categoryConverter.toResponse(it))
                .postList(postService.getMinimalPostList(it, 10))
                .build()
            )
            .toList();
    }
}
