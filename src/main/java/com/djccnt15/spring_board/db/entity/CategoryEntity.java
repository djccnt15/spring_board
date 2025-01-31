package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;

import java.util.List;

@Data
@Entity
@Table(name = "category")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CategoryEntity extends BaseEntity {
    
    @Column
    @NotNull
    private Integer tier;
    
    @Column(unique = true, length = 50)
    @NotBlank
    private String name;
    
    @Column(name = "is_active")
    @ColumnDefault(value = "true")  // annotation for ddl-auto
    @Builder.Default  // annotation for lombok default
    @NotNull
    private boolean isActive = true;
    
    @Column(name = "pin_order", unique = true)
    private Integer pinOrder;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // prevent circular toString bug
    private List<CategoryEntity> children;
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PostEntity> postEntityList;
}
