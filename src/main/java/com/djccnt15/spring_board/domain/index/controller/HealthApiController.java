package com.djccnt15.spring_board.domain.index.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HealthApiController {
    
    /**
     * API controller for health call
     * @return 1
     */
    @GetMapping(path = "/health")
    public ResponseEntity<Integer> health() {
        return ResponseEntity.ok(1);
    }
    
    /**
     * API controller for ping call
     * @return "pong"
     */
    @GetMapping(path = "/ping")
    public ResponseEntity<String> ping() {
        return ResponseEntity.ok("pong");
    }
}
