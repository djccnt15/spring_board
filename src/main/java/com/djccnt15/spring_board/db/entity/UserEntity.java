package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.enums.UserRoleEnum;
import com.djccnt15.spring_board.db.entity.id.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
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
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;
    
    @Column(name = "role")
    // @Enumerated(EnumType.STRING)  // easy to use but lowers DB performance, use enum converter
    private UserRoleEnum role;
    
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
