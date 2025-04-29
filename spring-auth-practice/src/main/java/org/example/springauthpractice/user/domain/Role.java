package org.example.springauthpractice.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.springauthpractice.common.exception.CustomException;
import org.example.springauthpractice.common.exception.ErrorCode;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Role {
    USER("ROLE_USER", "USER"),
    ADMIN("ROLE_ADMIN", "ADMIN");
    private final String roleSecurity;
    private final String roleName;

    public static Role of(String role) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getRoleName().equals(role) || r.getRoleSecurity().equals(role))
                .findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.ROLE_INVALID));
    }
}

