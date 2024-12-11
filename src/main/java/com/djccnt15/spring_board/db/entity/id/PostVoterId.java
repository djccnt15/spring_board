package com.djccnt15.spring_board.db.entity.id;

import lombok.*;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostVoterId implements Serializable {
    
    private Long post;
    
    private Long user;
}
