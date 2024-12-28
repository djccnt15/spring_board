package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminCategoryBusiness;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping(path = "/admin/category")
@RequiredArgsConstructor
public class AdminCategoryController {
    
    private final AdminCategoryBusiness business;
    
    /**
     * category admin page controller
     * @param model inject from spring
     * @param main selected main category to show sub category
     * @return category admin page view
     */
    @GetMapping
    public String manageCategory(
        Model model,
        @RequestParam(value = "main", required = false) Long main
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var categoryList = business.getCategories(main);
        model.addAttribute("categoryList", categoryList);
        
        var selectedMain = (categoryList.getMainList() == null)
            ? null
            : (main != null ? main : categoryList.getMainList().get(0).getId());
        model.addAttribute("selectedMain", selectedMain);
        model.addAttribute("mainCategoryForm", new CategoryCreateRequest());
        return "admin-category";
    }
}
