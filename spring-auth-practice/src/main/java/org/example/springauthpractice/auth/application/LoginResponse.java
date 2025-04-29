package org.example.springauthpractice.auth.application;

import lombok.Builder;

@Builder
public record LoginResponse(
        String accessToken,
        Long id,
        String name,
        String password
) {
}
