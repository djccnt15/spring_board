package com.djccnt15.spring_board.domain.admin.business;

import com.djccnt15.spring_board.annotations.Business;
import com.djccnt15.spring_board.domain.admin.model.AdminCategoryResponse;
import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Business
@RequiredArgsConstructor
public class AdminCategoryBusiness {
    
    private final CategoryService service;
    private final CategoryConverter converter;
    
    public AdminCategoryResponse getCategories(Long mainId) {
        var mainList = service.getCategoryByTier(1);
        
        if (mainList.isEmpty()) {
            return AdminCategoryResponse.builder().build();
        }
        
        var main = mainList.stream()
            .filter(it -> it.getId().equals(mainId)).findFirst()
            .orElse(mainList.get(0));
        var subList = service.getCategoryByMain(main);
        
        return AdminCategoryResponse.builder()
            .mainList(mainList.stream().map(converter::toResponse).toList())
            .subList(subList.stream().map(converter::toResponse).toList())
            .build();
    }
    
    public void createMain(CategoryCreateRequest form) {
        service.validateName(form);
        service.createCategory(form);
    }
    
    public void deleteCategory(Long id) {
        var entity = service.getCategory(id);
        service.deleteCategory(entity);
    }
}
