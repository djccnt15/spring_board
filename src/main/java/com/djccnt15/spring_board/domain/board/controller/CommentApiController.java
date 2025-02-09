package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.auth.model.UserSession;
import com.djccnt15.spring_board.domain.board.business.CommentBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class CommentApiController {
    
    private final CommentBusiness business;
    
    /**
     * rest controller for download comment history as csv file
     * @param mainCategory name of the category
     * @param postId post id
     * @param commentId comment id
     * @return comment history csv file
     */
    @GetMapping(path = "{mainCategory}/{postId}/comment/{commentId}/history/csvDownload")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> downloadCommentHistoryCsv(
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId
    ) {
        var response = business.createHistoryCsv(commentId);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=%s".formatted(response.getFileName())
            )
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(response.getContent());
    }
    
    /**
     * rest controller for download comment history as Excel file
     * @param user user session
     * @param mainCategory name of the category
     * @param postId post id
     * @param commentId comment id
     * @return comment history Excel file
     */
    @GetMapping(path = "{mainCategory}/{postId}/comment/{commentId}/history/excelDownload")
    @PreAuthorize(value = "hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<?> downloadCommentHistoryExcel(
        @AuthenticationPrincipal UserSession user,
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "postId") Long postId,
        @PathVariable(value = "commentId") Long commentId
    ) {
        var response = business.createHistoryExcel(user, commentId);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=%s".formatted(response.getFileName())
            )
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(response.getContent());
    }
}
