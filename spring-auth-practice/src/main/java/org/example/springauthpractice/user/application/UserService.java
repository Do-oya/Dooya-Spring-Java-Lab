package org.example.springauthpractice.user.application;

import org.example.springauthpractice.auth.application.LoginResponse;
import org.example.springauthpractice.user.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User signupUser(UserSignupRequest request);

    User findByToken(String email);
}
