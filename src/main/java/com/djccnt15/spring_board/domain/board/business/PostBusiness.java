package com.djccnt15.spring_board.domain.board.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.db.entity.PostEntity;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.model.*;
import com.djccnt15.spring_board.domain.board.service.CommentService;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
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
    private final CommentService commentService;
    
    public List<CategoryResponse> getCategoryList(String categoryName) {
        var mainCategory = categoryService.getCategory(categoryName);
        return categoryService.getCategoryByParent(mainCategory);
    }
    
    public PostEntity createPost(
        UserSession user,
        PostCreateRequest request
    ) {
        var userEntity = userService.getUser(user.getUserId());
        var category = categoryService.getCategory(request.getCategory());
        var post = postService.createPost(userEntity, category);
        postService.createContent(post, request);
        return post;
    }
    
    public PostListResponse getPostList(
        String mainCategoryName,
        Integer size,
        Integer page,
        String keyword,
        String categoryName
    ) {
        var mainCategory = categoryService.getCategory(mainCategoryName);
        var kw = "%%%s%%".formatted(keyword);
        var subCategoryId = categoryService.getOptionalCategory(categoryName)
            .map(CategoryEntity::getId)
            .orElse(null);
        var postList = postService.getPostList(mainCategory, size, page, kw, subCategoryId);
        var postListCount = postService.getPostListCount(mainCategory, kw, subCategoryId);
        var totalPageCount = (int) Math.ceil((double) postListCount / size);
        return PostListResponse.builder()
            .totalPages(totalPageCount)
            .postList(postList)
            .build();
    }
    
    public BoardIndexResponse getIndexPostList() {
        var mainCategoryList = categoryService.getCategoryByTier(1);
        var categoryResponseList = mainCategoryList.stream()
            .map(categoryConverter::toResponse)
            .toList();
        var boardList = mainCategoryList.stream()
            .map(it -> PostSummaryListResponse.builder()
                .category(categoryConverter.toResponse(it))
                .postList(postService.getMinimalPostList(it, 10))
                .build()
            )
            .toList();
        return BoardIndexResponse.builder()
            .categoryList(categoryResponseList)
            .boardList(boardList)
            .build();
    }
    
    public PostDetailResponse getPostDetail(Long id) {
        var post = postService.getPostDetail(id);
        var commentList = commentService.getCommentList(post);
        post.setCommentList(commentList);
        return post;
    }
    
    public PostUpdatePlaceholder getPostUpdatePlaceholder(
        UserSession user,
        String mainCategoryName,
        Long id
    ) {
        var post = postService.getPost(id);
        postService.validateAuthor(user, post);
        var mainCategory = categoryService.getCategory(mainCategoryName);
        var categoryList = categoryService.getCategoryByParent(mainCategory);
        var postContent = postService.getLastPostContent(post);
        return PostUpdatePlaceholder.builder()
            .categoryList(categoryList)
            .category(categoryConverter.toResponse(post.getCategory()))
            .title(postContent.getTitle())
            .content(postContent.getContent())
            .build();
    }
    
    public void updatePost(
        UserSession user,
        Long postId,
        PostCreateRequest request
    ) {
        var post = postService.getPost(postId);
        postService.validateAuthor(user, post);
        var category = categoryService.getCategory(request.getCategory());
        var postContent = postService.getLastPostContent(post);
        postService.updatePost(post, category);
        postService.updatePostContent(post, postContent, request);
    }
}
