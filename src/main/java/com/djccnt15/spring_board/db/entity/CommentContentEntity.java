package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment_content")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert  // annotation for using db default value for null field when insert
public class CommentContentEntity extends BaseEntity {
    
    @Column
    @ColumnDefault(value = "1")  // annotation for ddl-auto
    @Builder.Default  // annotation for lombok default
    @NotNull
    @Positive
    private int version = 1;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @JoinColumn(name = "comment_id")
    @ManyToOne
    private CommentEntity comment;
}
