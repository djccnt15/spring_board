package com.djccnt15.spring_board.domain.board.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PostListResponse {
    
    private Integer totalPages;
    
    private List<PostSummaryResponse> postList;
}
