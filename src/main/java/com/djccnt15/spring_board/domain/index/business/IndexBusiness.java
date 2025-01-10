package com.djccnt15.spring_board.domain.index.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.board.service.PostService;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import com.djccnt15.spring_board.domain.index.model.PostSummaryListResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Business
@RequiredArgsConstructor
public class IndexBusiness {
    
    private final CategoryService categoryService;
    private final CategoryConverter categoryConverter;
    private final PostService postService;
    
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
