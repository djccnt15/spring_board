package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "post_content")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert  // annotation for using db default value for null field when insert
public class PostContentEntity extends BaseEntity {
    
    @Column
    @ColumnDefault(value = "1")  // annotation for ddl-auto
    @Builder.Default  // annotation for lombok builder default
    @NotNull
    @Positive
    private int version = 1;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @Column(length = 200)
    @NotBlank
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String content;
    
    @JoinColumn(name = "post_id")
    @ManyToOne
    private PostEntity post;
}
