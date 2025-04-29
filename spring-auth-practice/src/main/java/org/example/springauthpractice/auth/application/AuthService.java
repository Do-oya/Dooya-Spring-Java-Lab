package org.example.springauthpractice.auth.application;

import org.example.springauthpractice.auth.presentation.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    LoginResponse login(LoginRequest request);
}
