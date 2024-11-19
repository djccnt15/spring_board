package com.djccnt15.spring_board.domain.index.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(path = "/")
public class IndexController {
    
    /**
     * temporal index redirect to swagger-ui
     * @return redirect to swagger-ui
     */
    @GetMapping
    public ModelAndView redirectIndex(ModelMap model) {
        model.addAttribute("attribute", "redirectIndex");
        return new ModelAndView("redirect:/swagger-ui/index.html", model);
    }
}
