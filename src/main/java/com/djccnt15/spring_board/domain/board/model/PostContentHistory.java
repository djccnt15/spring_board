package com.djccnt15.spring_board.domain.board.model;

import com.djccnt15.spring_board.annotations.TableHeader;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostContentHistory {
    
    private Long id;
    
    @TableHeader(value = "작성일시")
    private String createdDateTime;
    
    @TableHeader(value = "제목")
    private String title;
    
    @TableHeader(value = "내용")
    private String content;
}
