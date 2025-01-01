package com.djccnt15.spring_board.domain.qna.model;

import com.djccnt15.spring_board.domain.user.model.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionResponse {
    
    private Long id;
    
    private String subject;
    
    private String content;
    
    private LocalDateTime createDateTime;
    
    private LocalDateTime updateDateTime;
    
    private List<AnswerResponse> answerList;
    
    private UserResponse author;
    
    private Set<UserResponse> voterList;
}
