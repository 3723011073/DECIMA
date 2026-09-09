package com.marketing.analytics.controller;

import com.marketing.analytics.dto.APIResponseDTO;
import com.marketing.analytics.dto.LoginRequest;
import com.marketing.analytics.dto.LoginResponse;
import com.marketing.analytics.dto.RefreshTokenRequestDTO;
import com.marketing.analytics.dto.RegisterRequestDTO;
import com.marketing.analytics.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/auth", "/api/v1/auth", "/api/v1"})
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(APIResponseDTO.success(response, "Login successful"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDTO.error(ex.getMessage(), "AUTHENTICATION_FAILED"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponseDTO<LoginResponse>> register(@Valid @RequestBody RegisterRequestDTO request) {
        try {
            LoginResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(APIResponseDTO.success(response, "User registered successfully"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest()
                    .body(APIResponseDTO.error(ex.getMessage(), "REGISTRATION_FAILED"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponseDTO<LoginResponse>> refresh(@Valid @RequestBody RefreshTokenRequestDTO request) {
        try {
            LoginResponse response = authService.refresh(request.getRefreshToken());
            return ResponseEntity.ok(APIResponseDTO.success(response, "Token refreshed successfully"));
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDTO.error(ex.getMessage(), "INVALID_REFRESH_TOKEN"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponseDTO<String>> logout() {
        return ResponseEntity.ok(APIResponseDTO.success("", "Logout successful"));
    }

    @GetMapping("/health")
    public ResponseEntity<APIResponseDTO<String>> health() {
        return ResponseEntity.ok(APIResponseDTO.success("OK", "Service is healthy"));
    }
}

