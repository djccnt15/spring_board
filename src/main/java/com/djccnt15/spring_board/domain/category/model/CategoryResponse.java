package com.djccnt15.spring_board.domain.category.model;

import com.djccnt15.spring_board.domain.category.model.enums.CategoryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
    
    private Long id;
    
    private Integer tier;
    
    private String name;
    
    private Integer pinOrder;
    
    private CategoryStatusEnum status;
    
    private Long mainId;
}
