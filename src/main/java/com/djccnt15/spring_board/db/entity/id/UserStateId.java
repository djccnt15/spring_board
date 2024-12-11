package com.djccnt15.spring_board.db.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Data
@Embeddable  // annotation for composite PK
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStateId implements Serializable {
    
    @Column(name = "state_id")
    private Long stateId;
    
    @Column(name = "user_id")
    private Long userId;
}
