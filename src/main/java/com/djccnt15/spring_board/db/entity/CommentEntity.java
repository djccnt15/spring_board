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
@Table(
    name = "comment",
    indexes = {
        @Index(columnList = "is_active"),
        @Index(columnList = "post_id"),
        @Index(columnList = "author_id")
    }
)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommentEntity extends BaseEntity {
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
    
    @Column(name = "is_active")
    @ColumnDefault(value = "true")  // annotation for ddl-auto
    @Builder.Default  // annotation for lombok default
    @NotNull
    private boolean isActive = true;
    
    @JoinColumn(name = "author_id")
    @ManyToOne
    private UserEntity author;
    
    @JoinColumn(name = "post_id")
    @ManyToOne
    @NotNull
    private PostEntity post;
    
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    @ToString.Exclude  // prevent circular toString bug
    private List<CommentContentEntity> commentContent;
    
    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<CommentVoterEntity> commentVoter;
}
