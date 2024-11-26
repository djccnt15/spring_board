package com.djccnt15.spring_board.db.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.util.List;

@Data
@Entity
@Table(name = "question")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DynamicInsert
public class QuestionEntity extends DateTimeEntity {
    
    @Column(length = 200)
    @NotEmpty
    private String subject;
    
    @Column(columnDefinition = "TEXT")
    @NotEmpty
    private String content;
    
    @OneToMany(mappedBy = "questionEntity", cascade = CascadeType.REMOVE)
    @ToString.Exclude  // prevent circular toString bug
    private List<AnswerEntity> answerEntityList;
}
