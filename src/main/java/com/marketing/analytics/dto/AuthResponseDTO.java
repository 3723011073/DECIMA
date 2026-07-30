package com.marketing.analytics.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {

    private Long userId;
    private String email;
    private String name;
    private String role;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
