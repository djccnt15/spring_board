package com.djccnt15.spring_board.domain.board.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionForm {

    @NotEmpty(message = "TITLE is essential")
    @Size(max = 200)
    private String subject;
    
    @NotEmpty(message = "CONTENT is essential")
    private String content;
}
