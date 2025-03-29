package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/board")
@PreAuthorize(value = "isAuthenticated()")
@RequiredArgsConstructor
public class PostPrivateApiController {
    
    private final PostBusiness business;
    
    /**
     * rest controller for delete post
     * @param user user session
     * @param mainCategory name of the main category
     * @param id post id
     * @return redirect url after delete post
     */
    @DeleteMapping(path = "/{mainCategory}/{id}")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'WRITER')")
    public ResponseEntity<?> deletePost(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id
    ) {
        business.deletePost(user, id);
        return ResponseEntity
            .status(HttpStatus.SEE_OTHER)
            .header("redirectUrl", "/board/%s".formatted(mainCategory))
            .build();
    }
}
