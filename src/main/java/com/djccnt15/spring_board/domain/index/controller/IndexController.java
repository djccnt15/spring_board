package com.djccnt15.spring_board.domain.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(path = "/")
public class IndexController {
    
    /**
     * controller for handling index page
     * @return redirect url
     */
    @GetMapping
    public String redirectIndex() {
        return "redirect:/board";
    }
}
