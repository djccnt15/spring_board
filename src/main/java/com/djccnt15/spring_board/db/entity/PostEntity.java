package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "post")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PostEntity extends BaseEntity {
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
    
    @Column(name = "is_active")
    @ColumnDefault(value = "true")  // annotation for ddl-auto
    @Builder.Default  // annotation for lombok default
    @NotNull
    private boolean isActive = true;
    
    @Column(name = "view_count")
    @ColumnDefault(value = "0")
    @Builder.Default
    @NotNull
    private int views = 0;
    
    @JoinColumn(name = "category_id")
    @ManyToOne
    private CategoryEntity category;
    
    @JoinColumn(name = "author_id")
    @ManyToOne
    private UserEntity author;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @ToString.Exclude  // prevent circular toString bug
    private List<PostContentEntity> postContent;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<CommentEntity> comment;
    
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<PostVoterEntity> postVoter;
}
