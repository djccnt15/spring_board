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
     * api controller for creating main category
     * @param form user request form for create category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return ResponseEntity
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
        business.createMainCategory(form);
        return ResponseEntity.ok(ResponseMessageEnum.CREATE);
    }
    
    /**
     * api controller for update main category
     * @param id id of category to update
     * @param form user request form for update category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return ResponseEntity
     */
    @PatchMapping(path = "/main/form/{id}")
    public ResponseEntity<?> updateMainCategory(
        @PathVariable(value = "id") Long id,
        @Valid @ModelAttribute(name = "form") CategoryCreateRequest form,
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
        business.updateMainCategory(id, form);
        return ResponseEntity.ok(ResponseMessageEnum.UPDATE);
    }
    
    /**
     * api controller for create sub category
     * @param form user request form for create category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return ResponseEntity
     */
    @PostMapping(path = "/sub")
    public ResponseEntity<?> createSubCategory(
        @Valid @ModelAttribute(name = "subCategoryForm") CategoryCreateRequest form,
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
        business.createSubCategory(form);
        return ResponseEntity.ok(ResponseMessageEnum.CREATE);
    }
    
    /**
     * api controller for update sub category
     * @param id if of category to update
     * @param form user request form for update category
     * @param bindingResult validated result of the form. this must come right after the form
     * @return ResponseEntity
     */
    @PatchMapping(path = "/sub/form/{id}")
    public ResponseEntity<?> updateSubCategory(
        @PathVariable(value = "id") Long id,
        @Valid @ModelAttribute(name = "form") CategoryCreateRequest form,
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
        business.updateSubCategory(id, form);
        return ResponseEntity.ok(ResponseMessageEnum.UPDATE);
    }
}
