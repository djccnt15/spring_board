package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.DateTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.util.Set;

@Data
@Entity
@Table(name = "answer")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
public class AnswerEntity extends DateTimeEntity {
    
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String content;
    
    @JoinColumn(nullable = false, name = "question_id")
    @ManyToOne
    @NotNull
    private QuestionEntity questionEntity;
    
    @JoinColumn(name = "author_id")
    @ManyToOne
    private UserEntity author;
    
    @ManyToMany
    private Set<UserEntity> voter;
}
