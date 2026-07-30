package com.marketing.analytics.service;

import com.marketing.analytics.dto.AuthRequestDTO;
import com.marketing.analytics.dto.AuthResponseDTO;
import com.marketing.analytics.dto.RegisterRequestDTO;
import com.marketing.analytics.entity.User;
import com.marketing.analytics.exception.ResourceNotFoundException;
import com.marketing.analytics.repository.UserRepository;
import com.marketing.analytics.security.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // TokenBlacklist to store invalidated tokens
    private final Set<String> tokenBlacklist = new HashSet<>();

    public AuthResponseDTO login(AuthRequestDTO authRequest) throws AuthenticationException {
        log.info("Login attempt for user: {}", authRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new AuthenticationException("User account is inactive") {};
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);

        log.info("User {} successfully authenticated", user.getEmail());

        return AuthResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(86400000L) // 24 hours
                .build();
    }

    public AuthResponseDTO register(RegisterRequestDTO registerRequest) {
        log.info("Registration attempt for user: {}", registerRequest.getEmail());

        // Validate passwords match
        if (!registerRequest.getPassword().equals(registerRequest.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Validate password strength
        if (registerRequest.getPassword().length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }

        // Check if user already exists
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        // Determine role
        User.Role role = User.Role.ANALYST; // Default role
        if (registerRequest.getRole() != null && !registerRequest.getRole().isEmpty()) {
            try {
                role = User.Role.valueOf(registerRequest.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                log.warn("Invalid role provided: {}. Defaulting to ANALYST", registerRequest.getRole());
            }
        }

        User user = User.builder()
                .email(registerRequest.getEmail())
                .name(registerRequest.getName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(role)
                .isActive(true)
                .build();

        User savedUser = userRepository.save(user);
        log.info("User {} successfully registered with role {}", savedUser.getEmail(), role);

        // Generate tokens for immediate login
        String accessToken = jwtUtil.generateAccessToken(savedUser);
        String refreshToken = jwtUtil.generateRefreshToken(savedUser);

        return AuthResponseDTO.builder()
                .userId(savedUser.getUserId())
                .email(savedUser.getEmail())
                .name(savedUser.getName())
                .role(savedUser.getRole().name())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .expiresIn(86400000L)
                .build();
    }

    public AuthResponseDTO refreshToken(String refreshToken) {
        log.info("Token refresh attempt");

        // Check if token is blacklisted
        if (tokenBlacklist.contains(refreshToken)) {
            throw new IllegalArgumentException("Refresh token has been invalidated");
        }

        if (!jwtUtil.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        String email = jwtUtil.extractUsername(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.isEnabled()) {
            throw new IllegalArgumentException("User account is inactive");
        }

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        log.info("Token successfully refreshed for user: {}", email);

        return AuthResponseDTO.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .role(user.getRole().name())
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .expiresIn(86400000L)
                .build();
    }

    public void logout(String token) {
        if (token != null && !token.isEmpty()) {
            tokenBlacklist.add(token);
            log.info("Token added to blacklist for logout");
        }
    }

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        if (tokenBlacklist.contains(token)) {
            return false;
        }

        return jwtUtil.isTokenValid(token);
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public void deactivateUser(Long userId) {
        User user = getUserById(userId);
        user.setIsActive(false);
        userRepository.save(user);
        log.info("User {} has been deactivated", user.getEmail());
    }

    public void activateUser(Long userId) {
        User user = getUserById(userId);
        user.setIsActive(true);
        userRepository.save(user);
        log.info("User {} has been activated", user.getEmail());
    }
}
