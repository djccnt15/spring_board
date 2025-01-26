package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.CommentBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class CommentController {
    
    public final CommentBusiness business;
    
    /**
     * view controller for comment history
     * @param model inject from spring
     * @param mainCategory name of the category
     * @param postId post id
     * @param commentId comment id
     * @return comment history view
     */
    @GetMapping(path = "{mainCategory}/{postId}/comment/{commentId}/history")
    public String getCommentHistory(
        Model model,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId
    ) {
        var response = business.getCommentHistory(commentId);
        model.addAttribute("response", response);
        return "comment-history";
    }
}
