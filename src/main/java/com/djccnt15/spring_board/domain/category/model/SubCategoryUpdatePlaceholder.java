package com.djccnt15.spring_board.domain.category.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SubCategoryUpdatePlaceholder {
    
    private String name;
    
    private CategoryResponse selected;
    
    private List<CategoryResponse> mainList;
}
