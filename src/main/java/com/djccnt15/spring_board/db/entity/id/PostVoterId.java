package com.djccnt15.spring_board.db.entity.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostVoterId implements Serializable {
    
    private Long post;
    
    private Long user;
}
