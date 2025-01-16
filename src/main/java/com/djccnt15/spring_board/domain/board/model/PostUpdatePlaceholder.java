package com.djccnt15.spring_board.domain.board.model;

import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostUpdatePlaceholder {
    
    private List<CategoryResponse> categoryList;
    
    private CategoryResponse category;
    
    private String title;
    
    private String content;
}
