package org.example.springauthpractice.auth.application;

import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.example.springauthpractice.user.infrastructure.UserJpaRepository;
import org.example.springauthpractice.user.infrastructure.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private JwtUtil jwtUtil;

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest() {
        // given
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User mockUser = User.builder()
                .id(1L)
                .email("testEmail")
                .name("testName")
                .password(passwordEncoder.encode("testPassword"))
                .role(Role.USER)
                .build();
        given(userJpaRepository.findByEmail(mockUser.getEmail())).willReturn(mockUser);

        UserRepository userRepository = new UserRepositoryImpl(userJpaRepository);
        AuthService authService = new AuthServiceImpl(userRepository, jwtUtil, passwordEncoder);

        LoginRequest request = new LoginRequest("testEmail", "testPassword");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(mockUser.getEmail()).isEqualTo(response.email());
        then(userJpaRepository).should(times(1)).findByEmail(mockUser.getEmail());
    }
}
