package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.CommentBusiness;
import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping(path = "/board")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class CommentPrivateController {
    
    private final PostBusiness postBusiness;
    private final CommentBusiness commentBusiness;
    
    /**
     * controller for create comment
     * @param model inject from spring
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId id of the post
     * @param size size of comment list
     * @param page number of page
     * @param request data model for comment create
     * @param bindingResult validated result. this must come right after the form
     * @return redirect to anchor in post page
     */
    @PostMapping(path = "/{mainCategory}/{postId}/comment/form")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String createComment(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable String mainCategory,
        @PathVariable Long postId,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @Valid @ModelAttribute(name = "commentForm") CommentCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var postDetail = postBusiness.getPostDetail(postId, size, page);
            model.addAttribute(mainCategory);
            model.addAttribute("size", size);
            model.addAttribute("page", page);
            model.addAttribute("response", postDetail);
            return "post-detail";
        }
        commentBusiness.createComment(user, request, postId);
        return "redirect:/board/{mainCategory}/{postId}#comment-list";
    }
    
    /**
     * view controller for comment update
     * @param model inject from spring
     * @param user user session
     * @param commentId comment id
     * @return comment update view
     */
    @GetMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/form")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String getUpdateForm(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long commentId
    ) {
        model.addAttribute("id", commentId);
        model.addAttribute("form", new CommentCreateRequest());
        var placeholder = commentBusiness.getCommentUpdatePlaceholder(user, commentId);
        model.addAttribute("placeholder", placeholder);
        return "comment-update-form";
    }
    
    /**
     * controller for comment update
     * @param model inject from spring
     * @param user user session
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @param request data model for comment update
     * @param bindingResult validated result. this must come right after the form
     * @return redirect to anchor in post detail page
     */
    @PutMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/form")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String updateComment(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long commentId,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        @Valid @ModelAttribute(name = "form") CommentCreateRequest request,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            var placeholder = commentBusiness.getCommentUpdatePlaceholder(user, commentId);
            model.addAttribute("placeholder", placeholder);
            return "comment-update-form";
        }
        commentBusiness.updateComment(user, commentId, request);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        return "redirect:/board/{mainCategory}/{postId}#comment-{commentId}";
    }
    
    /**
     * rest controller for comment delete
     * @param user user session
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @return redirect to anchor in post detail page
     */
    @DeleteMapping(path = "/{mainCategory}/{postId}/comment/{commentId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String deleteComment(
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long commentId,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        RedirectAttributes redirectAttributes
    ) {
        commentBusiness.deleteComment(user, commentId);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        return "redirect:/board/{mainCategory}/{postId}#comment-list";
    }
    
    /**
     * controller for vote comment
     * @param user user session
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @return redirect to anchor of the comment
     */
    @GetMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/vote")
    public String voteComment(
        @AuthenticationPrincipal UserSession user,
        @PathVariable Long commentId,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "0") int page,
        RedirectAttributes redirectAttributes
    ) {
        commentBusiness.voteComment(user, commentId);
        redirectAttributes.addAttribute("page", page);
        redirectAttributes.addAttribute("size", size);
        return "redirect:/board/{mainCategory}/{postId}#comment-{commentId}";
    }
}
