package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.CommentBusiness;
import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class CommentPrivateController {
    
    private final PostBusiness postBusiness;
    private final CommentBusiness commentBusiness;
    
    @PostMapping(path = "/{mainCategory}/{id}/comment/form")
    public String createComment(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long postId,
        @Valid @ModelAttribute(name = "commentForm") CommentCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var postDetail = postBusiness.getPostDetail(postId);
            model.addAttribute(mainCategory);
            model.addAttribute("response", postDetail);
            return "post-detail";
        }
        commentBusiness.createComment(user, request, postId);
        return "redirect:/board/%s/%s".formatted(mainCategory, postId);
    }
}
