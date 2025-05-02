package org.example.springauthpractice.user.application;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserSignupRequest(
        @NotBlank(message = "이메일은 비어 있을 수 없습니다")
        String email,

        @NotBlank(message = "이름은 비어 있을 수 없습니다")
        String name,

        @NotBlank(message = "비밀번호는 비어 있을 수 없습니다")
        String password
) {
}
