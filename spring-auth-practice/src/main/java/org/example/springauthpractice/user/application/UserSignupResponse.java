package org.example.springauthpractice.user.application;

public record UserSignupResponse(
        String email,
        String name,
        String password
) {
}
