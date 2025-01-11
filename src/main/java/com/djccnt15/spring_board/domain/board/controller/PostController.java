package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.PostBusiness;
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
     * @param categoryName name of board - main category
     * @param size size of post list
     * @param page number of page
     * @param keyword search keyword
     * @return post list view
     */
    @GetMapping(path = "/{categoryName}")
    public String getPostList(
        Model model,
        @PathVariable(value = "categoryName") String categoryName,
        @RequestParam(value = "size", defaultValue = "10") int size,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "kw", defaultValue = "") String keyword
    ) {
        var response = business.getPostList(categoryName, size, page, keyword);
        model.addAttribute("response", response);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("size", size);
        model.addAttribute("page", page);
        model.addAttribute("kw", keyword);
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
}
