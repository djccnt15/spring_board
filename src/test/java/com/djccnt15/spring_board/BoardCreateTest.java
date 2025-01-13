package com.djccnt15.spring_board;

import com.djccnt15.spring_board.db.entity.CategoryEntity;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import com.djccnt15.spring_board.domain.board.model.PostCreateRequest;
import com.djccnt15.spring_board.domain.category.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardCreateTest {
    
    @Autowired
    private CategoryService categoryService;
    
    @Autowired
    private PostBusiness postBusiness;
    
    @Test
    void testCreatePost() {
        
        var session = UserSession.builder()
            .userId(6L)
            .build();
        
        var categoryList = categoryService.getCategoryByTier(2);
        for (CategoryEntity category : categoryList) {
            for (int i = 0; i < 100; i++) {
                var request = PostCreateRequest.builder()
                    .category(category.getName())
                    .title("%s 카테고리. 테스트 데이터:[%03d]".formatted(category.getName(), i))
                    .content("테스트 데이터")
                    .build();
                
                postBusiness.createPost(session, request);
            }
        }
    }
}
