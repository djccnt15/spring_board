package com.djccnt15.spring_board.domain.category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryCreateRequest {
    
    @NotNull(message = "TIER is essential")
    private Integer tier;
    
    @NotBlank(message = "NAME field must contain at least one non-whitespace character")
    @Size(max = 50)
    private String name;
    
    private Long mainId;
}
