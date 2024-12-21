package com.djccnt15.spring_board.domain.admin.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ManagerRoleActionEnum {
    
    GRANT("GRANT"),
    REVOKE("REVOKE")
    ;
    
    private final String value;
}
