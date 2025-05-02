package org.example.springauthpractice.fixture;

import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserFixture {
    public static User testUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email("testEmail")
                .name("testName")
                .password(passwordEncoder.encode("testPassword"))
                .role(Role.of("USER"))
                .build();
    }
}
