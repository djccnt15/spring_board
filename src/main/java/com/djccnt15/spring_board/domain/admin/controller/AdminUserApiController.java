package com.djccnt15.spring_board.domain.admin.controller;

import com.djccnt15.spring_board.domain.admin.business.AdminUserBusiness;
import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.user.model.DetailForm;
import com.djccnt15.spring_board.enums.ResponseMessageEnum;
import com.djccnt15.spring_board.exception.advice.model.ErrorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@Slf4j
@RestController
@RequestMapping(path = "/admin/user")
@RequiredArgsConstructor
public class AdminUserApiController {
    
    private final AdminUserBusiness business;
    
    /**
     * ban user
     * @param user user session
     * @param id user id
     * @param page number of page
     * @param form user ban form for request body
     * @param bindingResult validated result of the form. this must come right after the form
     * @return redirect to admin/ user page for refresh
     */
    @PutMapping(path = "/{id}/ban")
    public ResponseEntity<?> ban(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "id") Long id,
        @RequestParam(value = "page", defaultValue = "0") int page,
        @Valid DetailForm form,
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
        business.banUser(user, id, form);
        return ResponseEntity
            .created(URI.create("/admin/user?page=%s".formatted(page)))
            .body(ResponseMessageEnum.CREATE);
    }
}
