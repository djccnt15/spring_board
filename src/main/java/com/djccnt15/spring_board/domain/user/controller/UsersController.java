package com.djccnt15.spring_board.domain.user.controller;

import com.djccnt15.spring_board.domain.user.business.UserBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping(path = "/users")
@RequiredArgsConstructor
public class UsersController {
    
    private final UserBusiness business;
    
    /**
     * view controller for public user info
     * @param model inject from spring
     * @param id user id
     * @param size size of post list
     * @param page number of post list page
     * @return user info and post list page
     */
    @GetMapping(path = "/{id}/post-list")
    public String getUserPostList(
        Model model,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        model.addAttribute("showUserInfoLeftNav", true);
        model.addAttribute("id", id);
        var userInfo = business.getUserInfoPostLIst(id, size, page);
        model.addAttribute("response", userInfo);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "user-info-post-list";
    }
    
    /**
     * view controller for public user info
     * @param model inject from spring
     * @param id user id
     * @param size size of comment list page
     * @param page number of comment list page
     * @return user info and comment list page
     */
    @GetMapping(path = "/{id}/comment-list")
    public String getUserCommentList(
        Model model,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        model.addAttribute("showUserInfoLeftNav", true);
        model.addAttribute("id", id);
        var userInfo = business.getUserInfoCommentLIst(id, size, page);
        model.addAttribute("response", userInfo);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        return "user-info-comment-list";
    }
}
