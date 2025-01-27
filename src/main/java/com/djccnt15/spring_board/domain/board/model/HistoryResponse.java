package com.djccnt15.spring_board.domain.board.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryResponse {
    
    private String tableName;
    
    private byte[] tableData;
}
