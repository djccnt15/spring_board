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
@Table(
    name = "comment_voter",
    indexes = {
        @Index(columnList = "comment_id")
    }
)
@IdClass(CommentVoterId.class)  // annotation for composite PK
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVoterEntity {
    
    @Id
    @JoinColumn(name = "comment_id", nullable = false)
    @ManyToOne
    private CommentEntity comment;
    
    @Id
    @JoinColumn(name = "user_id", nullable = false)
    @ManyToOne
    private UserEntity user;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
}
