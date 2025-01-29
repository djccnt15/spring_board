package com.djccnt15.spring_board.domain.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserPostResponse extends UserItemResponse {
    
    private String mainCategory;

    private String subCategory;
    
    private String title;
    
    private Integer viewCount;
    
    private Integer commentCount;
}
