package com.djccnt15.spring_board.domain.admin.model;

import com.djccnt15.spring_board.domain.admin.model.enums.ManagerRoleActionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerRoleRequest {
    
    private Long id;
    
    private ManagerRoleActionEnum action;
}
