package com.djccnt15.spring_board.db.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Embeddable  // annotation for composite PK
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStateId implements Serializable {
    
    @Column(name = "state_id")
    private Long stateId;
    
    @Column(name = "user_id")
    private Long userId;
}
