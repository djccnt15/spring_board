package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Entity
@Table(name = "state")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StateEntity extends BaseEntity {
    
    @Column(nullable = false)
    @NotBlank
    private String name;
    
    @OneToMany(mappedBy = "state", cascade = CascadeType.REMOVE)
    @ToString.Exclude  // prevent circular toString bug
    private Set<UserStateEntity> userState;
}
