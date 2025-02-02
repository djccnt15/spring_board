package com.djccnt15.spring_board.domain.category.model;

import com.djccnt15.spring_board.domain.category.model.enums.CategoryOrderToEnum;
import lombok.Data;

@Data
public class CategoryOrderUpdateRequest {
    
    private CategoryOrderToEnum to;
}
