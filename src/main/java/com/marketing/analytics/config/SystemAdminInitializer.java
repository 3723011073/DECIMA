package com.marketing.analytics.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.marketing.analytics.entity.User;
import com.marketing.analytics.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SystemAdminInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner createDefaultSystemAdmin() {
        return args -> {
            if (!userRepository.existsByEmail("admin@decima.ai")) {
                userRepository.save(User.builder()
                        .email("admin@decima.ai")
                        .name("DECIMA System Owner")
                        .password(passwordEncoder.encode("Admin@12345"))
                        .role(User.Role.SYSTEM_ADMIN)
                        .status("ACTIVE")
                        .build());
            }
        };
    }
}