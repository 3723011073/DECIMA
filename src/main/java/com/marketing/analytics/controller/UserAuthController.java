package com.marketing.analytics.controller;

import com.marketing.analytics.dto.APIResponseDTO;
import com.marketing.analytics.dto.AuthRequestDTO;
import com.marketing.analytics.dto.AuthResponseDTO;
import com.marketing.analytics.dto.RefreshTokenRequestDTO;
import com.marketing.analytics.dto.RegisterRequestDTO;
import com.marketing.analytics.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<APIResponseDTO<AuthResponseDTO>> login(
            @Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            AuthResponseDTO response = userService.login(authRequest);
            return ResponseEntity.ok(APIResponseDTO.success(response, "Login successful"));
        } catch (AuthenticationException e) {
            log.error("Authentication failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDTO.error("Invalid email or password", "AUTHENTICATION_FAILED"));
        } catch (Exception e) {
            log.error("Login error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDTO.error("Login failed", "LOGIN_ERROR"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<APIResponseDTO<AuthResponseDTO>> register(
            @Valid @RequestBody RegisterRequestDTO registerRequest) {
        try {
            AuthResponseDTO response = userService.register(registerRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(APIResponseDTO.success(response, "User registered successfully"));
        } catch (IllegalArgumentException e) {
            log.error("Registration failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(APIResponseDTO.error(e.getMessage(), "REGISTRATION_FAILED"));
        } catch (Exception e) {
            log.error("Registration error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDTO.error("Registration failed", "REGISTRATION_ERROR"));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<APIResponseDTO<AuthResponseDTO>> refreshToken(
            @Valid @RequestBody RefreshTokenRequestDTO refreshRequest) {
        try {
            AuthResponseDTO response = userService.refreshToken(refreshRequest.getRefreshToken());
            return ResponseEntity.ok(APIResponseDTO.success(response, "Token refreshed successfully"));
        } catch (IllegalArgumentException e) {
            log.error("Token refresh failed: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(APIResponseDTO.error(e.getMessage(), "INVALID_REFRESH_TOKEN"));
        } catch (Exception e) {
            log.error("Token refresh error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDTO.error("Token refresh failed", "REFRESH_ERROR"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<APIResponseDTO<String>> logout(HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            userService.logout(token);
            return ResponseEntity.ok(APIResponseDTO.success("", "Logout successful"));
        } catch (Exception e) {
            log.error("Logout error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(APIResponseDTO.error("Logout failed", "LOGOUT_ERROR"));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<APIResponseDTO<Boolean>> validateToken(HttpServletRequest request) {
        try {
            String token = extractTokenFromRequest(request);
            boolean isValid = userService.validateToken(token);
            return ResponseEntity.ok(APIResponseDTO.success(isValid, "Token validation completed"));
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            return ResponseEntity.ok(APIResponseDTO.success(false, "Token is invalid"));
        }
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
