package com.djccnt15.spring_board.domain.category.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryCreateRequest {
    
    private Integer tier;
    
    @NotBlank(message = "NAME field must contain at least one non-whitespace character")
    @Size(max = 50)
    private String name;
    
    private Long mainId;
}
