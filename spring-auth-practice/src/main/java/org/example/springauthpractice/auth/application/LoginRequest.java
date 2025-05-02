package org.example.springauthpractice.auth.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record LoginRequest(
        @NotBlank(message = "이메일은 비어 있을 수 없습니다")
        String email,

        @NotBlank(message = "비밀번호는 비어 있을 수 없습니다")
        String password
) {
}

