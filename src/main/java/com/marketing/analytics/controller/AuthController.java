package com.marketing.analytics.controller;

import com.marketing.analytics.dto.APIResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @GetMapping("/health")
    public ResponseEntity<APIResponseDTO<String>> health() {
        return ResponseEntity.ok(APIResponseDTO.success("OK", "Service is healthy"));
    }
}

