package com.djccnt15.spring_board.utils.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileResponse {
    
    private String fileName;
    
    private byte[] content;
}
