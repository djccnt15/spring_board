package com.djccnt15.spring_board.db.entity.id;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentVoterId implements Serializable {

    private Long comment;
    
    private Long user;
}
