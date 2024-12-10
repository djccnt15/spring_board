package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Entity
@Table(name = "user_info")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {
    
    @Column(unique = true, length = 25)
    @NotBlank
    private String username;
    
    @NotBlank
    private String password;
    
    @Column(unique = true)
    @NotNull
    private String email;
    
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<QuestionEntity> question;
    
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<AnswerEntity> answer;
}
