package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
    private Integer tier;
    
    @Column(unique = true)
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private CategoryEntity parent;
    
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // prevent circular toString bug
    private List<CategoryEntity> children;
    
    public void addChild(CategoryEntity child) {
        child.setParent(this);
        children.add(child);
    }
    
    public void removeChild(CategoryEntity child) {
        child.setParent(null);
        children.remove(child);
    }
    
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<PostEntity> postEntityList;
}
