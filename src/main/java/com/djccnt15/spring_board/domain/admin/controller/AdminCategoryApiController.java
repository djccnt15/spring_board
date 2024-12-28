package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminCategoryBusiness;
import com.djccnt15.spring_board.domain.category.model.CategoryCreateRequest;
import com.djccnt15.spring_board.enums.ResponseMessageEnum;
import com.djccnt15.spring_board.exception.advice.model.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(path = "/admin/category")
@RequiredArgsConstructor
public class AdminCategoryApiController {
    
    private final AdminCategoryBusiness business;
    
    /**
     * controller for creating main category
     * @param form user request form for create main category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return redirect to admin category page
     */
    @PostMapping(path = "/main")
    public ResponseEntity<?> createMainCategory(
        @Valid @ModelAttribute(name = "mainCategoryForm") CategoryCreateRequest form,
        BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            var errors = bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
            var errResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST)
                .message(String.join("\n", errors))
                .build();
            return ResponseEntity.badRequest().body(errResponse);
        }
        business.createMain(form);
        return ResponseEntity.ok(ResponseMessageEnum.CREATE);
    }
}