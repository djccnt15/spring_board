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
    @PostMapping(path = "/{mainCategory}/{id}/comment/form")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String createComment(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long postId,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
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
        return "redirect:/board/%s/%s#comment-list".formatted(mainCategory, postId);
    }
    
    /**
     * view controller for comment update
     * @param model inject from spring
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId post id
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @return comment update view
     */
    @GetMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/form")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String getUpdateForm(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
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
     * @param mainCategory name of the main category
     * @param postId post id
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
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @Valid @ModelAttribute(name = "form") CommentCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var placeholder = commentBusiness.getCommentUpdatePlaceholder(user, commentId);
            model.addAttribute("placeholder", placeholder);
            return "comment-update-form";
        }
        commentBusiness.updateComment(user, commentId, request);
        return "redirect:/board/%s/%s?page=%s&size=%s#comment-%s".formatted(mainCategory, postId, page, size, commentId);
    }
    
    /**
     * rest controller for comment delete
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId post id
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @return redirect to anchor in post detail page
     */
    @DeleteMapping(path = "/{mainCategory}/{postId}/comment/{commentId}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public String deleteComment(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        commentBusiness.deleteComment(user, commentId);
        return "redirect:/board/%s/%s?page=%s&size=%s#comment-list".formatted(mainCategory, postId, page, size);
    }
    
    /**
     * controller for vote comment
     * @param user user session
     * @param mainCategory name of the main category
     * @param postId post id
     * @param commentId comment id
     * @param size size of comment list
     * @param page number of page
     * @return redirect to anchor of the comment
     */
    @GetMapping(path = "/{mainCategory}/{postId}/comment/{commentId}/vote")
    public String voteComment(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        commentBusiness.voteComment(user, commentId);
        return "redirect:/board/%s/%s?page=%s&size=%s#comment-%s".formatted(mainCategory, postId, page, size, commentId);
    }
}
