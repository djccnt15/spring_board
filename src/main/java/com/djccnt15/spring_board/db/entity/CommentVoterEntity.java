package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.CommentVoterId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comment_voter")
@IdClass(CommentVoterId.class)  // annotation for composite PK
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVoterEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private CommentEntity comment;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
}
