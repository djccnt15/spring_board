package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import com.djccnt15.spring_board.domain.board.model.CommentCreateRequest;
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
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class PostController {
    
    private final PostBusiness business;
    
    /**
     * view controller of post list
     * @param model inject from spring
     * @param mainCategory name of board - main category
     * @param size size of post list
     * @param page number of page
     * @param keyword search keyword
     * @param category name of category - sub category
     * @return post list view
     */
    @GetMapping(path = "/{mainCategory}")
    public String getPostList(
        Model model,
        @PathVariable(value = "mainCategory") String mainCategory,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "kw", defaultValue = "") String keyword,
        @RequestParam(value = "category", defaultValue = "") String category
    ) {
        var response = business.getPostList(mainCategory, size, page, keyword, category);
        model.addAttribute("response", response);
        model.addAttribute(mainCategory);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("kw", keyword);
        model.addAttribute(category);
        return "post-list";
    }
    
    /**
     * view controller for board index page
     * @param model inject from spring
     * @return board index view
     */
    @GetMapping
    public String getBoardIndex(Model model) {
        var response = business.getIndexPostList();
        model.addAttribute("showLeftNav", true);
        model.addAttribute("response", response);
        return "board-index";
    }
    
    /**
     * view controller for post detail page
     * @param model inject from spring
     * @param mainCategory name of category
     * @param id id of post
     * @param size size of comment list
     * @param page number of page
     * @return post detail view
     */
    @GetMapping(path = "/{mainCategory}/{id}")
    public String getPostDetail(
        Model model,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page
    ) {
        var response = business.getPostDetail(id, size, page);
        model.addAttribute(mainCategory);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("response", response);
        model.addAttribute("commentForm", new CommentCreateRequest());
        return "post-detail";
    }
    
    /**
     * view controller for post history
     * @param model inject from spring
     * @param mainCategory name of category
     * @param id post id
     * @return post history view
     */
    @GetMapping(path = "{mainCategory}/{id}/history")
    public String getPostHistory(
        Model model,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id
    ) {
        var response = business.getHistory(id);
        model.addAttribute("response", response);
        model.addAttribute("id", id);
        return "post-history";
    }
}
