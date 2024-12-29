package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminCategoryBusiness;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
     * @param mainId selected main category to show sub category
     * @return category admin page view
     */
    @GetMapping
    public String manageCategory(
        Model model,
        @RequestParam(value = "main", required = false) Long mainId
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var categoryList = business.getCategories(mainId);
        model.addAttribute("categoryList", categoryList);
        
        var selectedMain = (categoryList.getMainList() == null)
            ? null
            : (mainId != null ? mainId : categoryList.getMainList().get(0).getId());
        model.addAttribute("selectedMain", selectedMain);
        model.addAttribute("mainCategoryForm", new CategoryCreateRequest());
        return "admin-category";
    }
    
    /**
     * controller for delete main category
     * @param id id of category to delete
     * @return redirect to admin category page
     */
    @DeleteMapping(path = "/{id}")
    public String deleteMainCategory(@PathVariable(value = "id") Long id) {
        business.deleteCategory(id);
        return "redirect:/admin/category";
    }
    
    /**
     * view controller for update category
     * @param model inject from spring
     * @param id id of category to update
     * @return update form page
     */
    @GetMapping(path = "/main/form/{id}")
    public String updateCategoryForm(
        Model model,
        @PathVariable(value = "id") Long id
    ) {
        model.addAttribute("showAdminLeftNav", true);
        var updateForm = business.getCategoryUpdateForm(id);
        model.addAttribute("placeholders", updateForm);
        model.addAttribute("form", new CategoryCreateRequest());
        return "category-main-update-form";
    }
}
