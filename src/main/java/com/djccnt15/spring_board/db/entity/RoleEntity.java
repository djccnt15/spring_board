package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Entity
@Table(name = "role")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RoleEntity extends BaseEntity {
    
    @Column
    @NotBlank
    private String name;
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.DETACH)
    @ToString.Exclude  // prevent circular toString bug
    private Set<UserEntity> userEntities;
}
