package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminCategoryBusiness;
import com.djccnt15.spring_board.domain.admin.business.AdminUserBusiness;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
        
        return "admin-category";
    }
    
    /**
     * view controller for main category create form
     * @param model inject from spring
     * @return main category create form view
     */
    @GetMapping(path = "/main")
    public String manageMainCategory(Model model) {
        model.addAttribute("showAdminLeftNav", true);
        model.addAttribute("form", new CategoryCreateRequest());
        return "category-main-form";
    }
    
    /**
     * controller for creating main category
     * @param model inject from spring
     * @param form user request form for create main category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return redirect to admin category page
     */
    @PostMapping(path = "/main")
    public String createMainCategory(
        Model model,
        @Valid @ModelAttribute(name = "form") CategoryCreateRequest form,
        BindingResult bindingResult
    ) {
        model.addAttribute("showAdminLeftNav", true);
        if (bindingResult.hasErrors()) {
            return "category-main-form";
        }
        business.createMain(form);
        return "redirect:/admin/category";
    }
}
