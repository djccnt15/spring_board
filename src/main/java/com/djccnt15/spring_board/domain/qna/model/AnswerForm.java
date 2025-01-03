package com.djccnt15.spring_board.domain.qna.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AnswerForm {
    
    @NotEmpty(message = "CONTENT is essential")
    private String content;
}
