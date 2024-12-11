package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment_content")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommentContentEntity extends BaseEntity {
    
    @Column
    @NotNull
    private Integer version;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String content;
    
    @JoinColumn(name = "comment_id")
    @ManyToOne
    private CommentEntity comment;
}
