package org.example.springauthpractice.user.application;

public record UserSignupRequest(
        String name,
        String password
) {
}
