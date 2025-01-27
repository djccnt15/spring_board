package com.djccnt15.spring_board.domain.board.model;

import com.djccnt15.spring_board.annotations.CsvHeader;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostContentHistory {
    
    private Long id;
    
    @CsvHeader(value = "작성일시")
    private String createdDateTime;
    
    @CsvHeader(value = "제목")
    private String title;
    
    @CsvHeader(value = "내용")
    private String content;
}
