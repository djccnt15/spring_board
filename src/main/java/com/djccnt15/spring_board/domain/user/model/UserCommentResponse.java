package com.djccnt15.spring_board.domain.user.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class UserCommentResponse extends UserItemResponse {
    
    private String category;
    
    private Long postId;
}
