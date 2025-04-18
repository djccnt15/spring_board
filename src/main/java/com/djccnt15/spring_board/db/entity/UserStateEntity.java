package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.UserStateId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user_state")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStateEntity {
    
    @EmbeddedId  // annotation for composite PK
    private UserStateId userStateId;
    
    @MapsId(value = "stateId")  // annotation for composite PK
    @JoinColumn(name = "state_id", nullable = false)
    @ManyToOne
    private StateEntity state;
    
    @MapsId(value = "userId")  // annotation for composite PK
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private UserEntity user;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
    
    @Column(nullable = false)
    @NotBlank
    private String detail;
}
