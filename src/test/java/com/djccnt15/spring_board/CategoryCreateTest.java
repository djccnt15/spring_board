package com.djccnt15.spring_board;

import com.djccnt15.spring_board.domain.category.converter.CategoryConverter;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CategoryCreateTest {
    
    @Autowired
    private CategoryService service;
    
    @Autowired
    private CategoryConverter converter;
    
    
    List<CategoryCreateRequest> mainCategoryList = List.of(
        new CategoryCreateRequest(null, "qna", null),
        new CategoryCreateRequest(null, "community", null)
    );
    
    List<CategoryCreateRequest> subCategoryList = List.of(
        new CategoryCreateRequest(null, "tech", 1L),
        new CategoryCreateRequest(null, "life", 2L),
        new CategoryCreateRequest(null, "news", 2L),
        new CategoryCreateRequest(null, "career", 1L)
    );
    
    @Test
    void testCreateCategory() {
        mainCategoryList.stream()
            .map(it -> converter.toEntity(it, 1))
            .forEach(service::createCategory);
        
        subCategoryList.stream()
            .map(it -> converter.toEntity(it, 2, service.getCategory(it.getMainId())))
            .forEach(service::createCategory);
    }
}
