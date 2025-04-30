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
        User mockUser = new User(1L, "testEmail", "testId", "testPassword", Role.USER);
        given(userJpaRepository.findByEmail(mockUser.getEmail())).willReturn(mockUser);

        UserRepository userRepository = new UserRepositoryImpl(userJpaRepository);
        AuthService authService = new AuthServiceImpl(userRepository, jwtUtil);

        LoginRequest request = new LoginRequest("testId", "testPassword");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(mockUser.getEmail()).isEqualTo(response.email());
        then(userJpaRepository).should(times(1)).findByEmail(mockUser.getEmail());
    }
}
