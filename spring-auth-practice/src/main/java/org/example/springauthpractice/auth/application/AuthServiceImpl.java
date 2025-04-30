package org.example.springauthpractice.auth.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.common.exception.CustomException;
import org.example.springauthpractice.common.exception.ErrorCode;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_MATCH_LOGIN_INFO));

        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .password(user.getPassword())
                .build();
    }
}
