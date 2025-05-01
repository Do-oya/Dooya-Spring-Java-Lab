package org.example.springauthpractice.user.application;

import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User signupUser(UserSignupRequest request) {
        return userRepository.save(User.signup(request.email(), request.name(), passwordEncoder.encode(request.password())));
    }
}
