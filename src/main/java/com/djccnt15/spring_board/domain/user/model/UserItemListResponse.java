package com.djccnt15.spring_board.domain.user.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserItemListResponse {
    
    private int totalPages;
    
    private List<?> itemList;
}
