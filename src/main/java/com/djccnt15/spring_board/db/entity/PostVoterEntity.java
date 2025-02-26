package com.djccnt15.spring_board.db.entity;

import com.djccnt15.spring_board.db.entity.id.PostVoterId;
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
    name = "post_voter",
    indexes = {
        @Index(columnList = "post_id")
    }
)
@IdClass(PostVoterId.class)  // annotation for composite PK
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVoterEntity {
    
    @Id
    @JoinColumn(name = "post_id")
    @ManyToOne
    private PostEntity post;
    
    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne
    private UserEntity user;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
}
