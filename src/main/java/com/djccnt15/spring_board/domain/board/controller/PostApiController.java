package com.djccnt15.spring_board.domain.board.controller;

import com.djccnt15.spring_board.domain.board.business.PostBusiness;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/board")
@RequiredArgsConstructor
public class PostApiController {
    
    private final PostBusiness business;
    
    /**
     * rest controller for download post history
     * @param mainCategory name of the category
     * @param id post id
     * @return post history table
     */
    @GetMapping(path = "{mainCategory}/{id}/history/fileDownload")
    public ResponseEntity<?> downloadPostHistory(
        @PathVariable(value = "mainCategory") String mainCategory,
        @PathVariable(value = "id") Long id
    ) {
        var response = business.createHistoryCsv(id);
        return ResponseEntity.ok()
            .header(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=%s".formatted(response.getFileName())
            )
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(response.getContent());
    }
}
