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
@Table(name = "post_voter")
@IdClass(PostVoterId.class)  // annotation for composite PK
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVoterEntity {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private PostEntity post;
    
    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    
    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdDatetime;
}
