package org.example.springauthpractice.user.application;

import lombok.Builder;
import org.example.springauthpractice.user.domain.Role;

@Builder
public record UserResponse(
        Long id,
        String email,
        String name,
        Role role
) {
}
