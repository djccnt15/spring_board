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
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/board/%s/%s#comment-form".formatted(mainCategory, postId);
    }
    
    /**
     * view controller for comment update
     * @param model inject from spring
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId post id
     * @param commentId comment id
     * @return comment update view
     */
    @GetMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/form")
    public String getUpdateForm(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId
    ) {
        model.addAttribute("form", new CommentCreateRequest());
        var placeholder = commentBusiness.getCommentUpdatePlaceholder(user, commentId);
        model.addAttribute("placeholder", placeholder);
        return "comment-update-form";
    }
    
    /**
     * controller for comment update
     * @param model inject from spring
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId post id
     * @param commentId comment id
     * @param request data model for comment update
     * @param bindingResult validated result. this must come right after the form
     * @return redirect anchor to post detail page
     */
    @PutMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/form")
    public String updateComment(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId,
        @Valid @ModelAttribute(name = "form") CommentCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var placeholder = commentBusiness.getCommentUpdatePlaceholder(user, commentId);
            model.addAttribute("placeholder", placeholder);
            return "comment-update-form";
        }
        commentBusiness.updateComment(user, commentId, request);
        return "redirect:/board/%s/%s#comment-%s".formatted(mainCategory, postId, commentId);
    }
}
