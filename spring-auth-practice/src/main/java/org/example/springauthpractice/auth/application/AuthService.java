package org.example.springauthpractice.auth.application;

import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(LoginRequest request);
}
