package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

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
    
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<PostEntity> post;
    
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    private List<CommentEntity> comment;
    
    @JoinColumn(name = "role_id")
    @ManyToOne
    private RoleEntity role;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<PostVoterEntity> postVoter;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<CommentVoterEntity> commentVoter;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<UserStateEntity> userState;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private Set<LoggedInEntity> loggedIn;
}
