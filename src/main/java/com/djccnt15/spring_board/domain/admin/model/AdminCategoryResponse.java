package com.djccnt15.spring_board.domain.admin.model;

import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminCategoryResponse {
    
    private List<CategoryResponse> mainList;
    
    private List<CategoryResponse> subList;
}
