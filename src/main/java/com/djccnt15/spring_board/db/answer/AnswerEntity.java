package com.djccnt15.spring_board.db.answer;

import com.djccnt15.spring_board.db.BaseEntity;
import com.djccnt15.spring_board.db.question.QuestionEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "answer")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
public class AnswerEntity extends BaseEntity {
    
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String content;
    
    @Column(insertable = false, updatable = false)
    @ColumnDefault(value = "now()")
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @JoinColumn(nullable = false, name = "question_id")
    @ManyToOne
    @NotNull
    private QuestionEntity questionEntity;
}
