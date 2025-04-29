package org.example.springauthpractice.user.application;

public record UserSignupResponse(
        String name,
        String password
) {
}
