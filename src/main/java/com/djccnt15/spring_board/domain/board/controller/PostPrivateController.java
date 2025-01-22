package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
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
public class PostPrivateController {
    
    private final PostBusiness business;
    
    /**
     * view controller to post create form
     * @param model inject from spring
     * @param mainCategory category name to create post
     * @return post create page
     */
    @GetMapping(path = "/{mainCategory}/form")
    public String getCreateForm(
        Model model,
        @PathVariable(value = "mainCategory") String mainCategory
    ) {
        var categoryList = business.getCategoryList(mainCategory);
        model.addAttribute(mainCategory);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("form", new PostCreateRequest());
        return "post-form";
    }
    
    /**
     * post create controller
     * @param user inject user session data
     * @param mainCategory main category name to create post
     * @param request actual post content data as form
     * @param bindingResult validated result. this must come right after the form
     * @return redirect to created post
     */
    @PostMapping(path = "/{mainCategory}/form")
    public String createPost(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @Valid @ModelAttribute(name = "form") PostCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var categoryList = business.getCategoryList(mainCategory);
            model.addAttribute(mainCategory);
            model.addAttribute("categoryList", categoryList);
            return "post-form";
        }
        var post = business.createPost(user, request);
        return "redirect:/board/%s/%s".formatted(post.getCategory().getParent().getName(), post.getId());
    }
    
    /**
     * view controller for post update page
     * @param model inject from spring
     * @param mainCategory name of the main category
     * @param id post id
     * @return post update view
     */
    @GetMapping(path = "/{mainCategory}/{id}/form")
    public String getUpdateForm(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id
    ) {
        var placeholder = business.getPostUpdatePlaceholder(user, mainCategory, id);
        model.addAttribute("placeholder", placeholder);
        model.addAttribute(mainCategory);
        model.addAttribute("form", new PostCreateRequest());
        return "post-update-form";
    }
    
    /**
     * controller for post update
     * @param model inject from spring
     * @param user user session
     * @param mainCategory name of the main category
     * @param id post id
     * @param request data model for post update
     * @param bindingResult validated result. this must come right after the form
     * @return redirect to post detail page
     */
    @PutMapping(path = "/{mainCategory}/{id}/form")
    public String updatePost(
        Model model,
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id,
        @Valid @ModelAttribute(name = "form") PostCreateRequest request,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var placeholder = business.getPostUpdatePlaceholder(user, mainCategory, id);
            model.addAttribute("placeholder", placeholder);
            model.addAttribute(mainCategory);
            return "post-update-form";
        }
        business.updatePost(user, id, request);
        return "redirect:/board/%s/%s".formatted(mainCategory, id);
    }
    
    /**
     * controller for vote post
     * @param user user session
     * @param mainCategory name of the main category
     * @param id post id
     * @return redirect to post page
     */
    @GetMapping(path = "/{mainCategory}/{id}/vote")
    public String votePost(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id
    ) {
        business.votePost(user, id);
        return "redirect:/board/%s/%s".formatted(mainCategory, id);
    }
}
