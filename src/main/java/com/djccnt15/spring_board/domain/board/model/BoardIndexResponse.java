package com.djccnt15.spring_board.domain.board.model;

import com.djccnt15.spring_board.domain.category.model.CategoryResponse;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class BoardIndexResponse {
    
    private List<CategoryResponse> categoryList;
    
    private List<PostSummaryListResponse> boardList;
}
