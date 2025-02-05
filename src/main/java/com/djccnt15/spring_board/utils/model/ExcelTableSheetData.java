package com.djccnt15.spring_board.utils.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ExcelTableSheetData<T> {
    
    List<T> records;
    
    Class<T> type;
    
    String sheetName;
}
