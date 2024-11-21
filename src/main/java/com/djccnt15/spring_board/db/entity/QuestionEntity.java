package com.djccnt15.spring_board.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "question")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
public class QuestionEntity extends BaseEntity {
    
    @Column(length = 200)
    @NotEmpty
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String content;
    
    @Column(insertable = false, updatable = false)
    @ColumnDefault(value = "now()")
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE)
    @ToString.Exclude  // prevent circular toString bug
    private List<AnswerEntity> answerEntityList;
}
