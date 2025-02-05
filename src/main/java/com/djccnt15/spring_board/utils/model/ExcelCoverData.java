package com.djccnt15.spring_board.utils.model;

import com.djccnt15.spring_board.annotations.TableHeader;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExcelCoverData {
    
    @TableHeader(value = "제목")
    private String title;
    
    @TableHeader(value = "생성자")
    private String creator;
    
    @TableHeader(value = "생성일")
    private String createDateTime;
}
