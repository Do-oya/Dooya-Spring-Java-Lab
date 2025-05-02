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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest_Success() {
        // given
        User testUser = User.builder()
                .id(1L)
                .email("testEmail")
                .name("testName")
                .password(passwordEncoder.encode("testPassword"))
                .role(Role.USER)
                .build();

        LoginRequest request = new LoginRequest("testEmail", "testPassword");

        given(userRepository.findByEmail(testUser.getEmail())).willReturn(Optional.of(testUser));
        given(passwordEncoder.matches(request.password(), testUser.getPassword())).willReturn(true);
        given(jwtUtil.createAccessToken(testUser.getId(), testUser.getEmail(), testUser.getRole())).willReturn("mocked-jwt-token");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(testUser.getEmail()).isEqualTo(response.email());
        then(userRepository).should(times(1)).findByEmail(testUser.getEmail());
        then(passwordEncoder).should(times(1)).matches(request.password(), testUser.getPassword());
        then(jwtUtil).should(times(1)).createAccessToken(testUser.getId(), testUser.getEmail(), testUser.getRole());
    }
}
