package com.djccnt15.spring_board.domain.board.model;

import com.djccnt15.spring_board.domain.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerResponse {
    
    private Long id;
    
    private String content;
    
    private LocalDateTime createDateTime;
    
    private LocalDateTime updateDateTime;
    
    private UserResponse author;
    
    private QuestionResponse question;
    
    private Set<UserResponse> voterList;
}
