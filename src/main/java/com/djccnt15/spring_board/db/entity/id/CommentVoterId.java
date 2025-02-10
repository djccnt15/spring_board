package com.djccnt15.spring_board.db.entity.id;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommentVoterId implements Serializable {

    private Long comment;
    
    private Long user;
}
