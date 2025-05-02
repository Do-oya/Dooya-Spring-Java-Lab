package org.example.springauthpractice.auth.application;

import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.common.exception.CustomException;
import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
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

    private User testUser;
    private LoginRequest request;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("testEmail")
                .name("testName")
                .password(passwordEncoder.encode("testPassword"))
                .role(Role.USER)
                .build();

        request = LoginRequest.builder()
                .email("testEmail")
                .password("testPassword")
                .build();
    }

    @DisplayName("로그인 성공 - 토큰 발급")
    @Test
    void loginTest_Success() {
        // given
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

    @DisplayName("로그인 실패 - 존재하지 않는 계정")
    @Test
    void loginTest_Fail_NotEmail() {
        // given
        given(userRepository.findByEmail(testUser.getEmail())).willReturn(Optional.empty());

        // when && then
        assertThatCode(() -> authService.login(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("로그인 정보에 해당하는 유저가 존재하지 않습니다.");
        then(userRepository).should(times(1)).findByEmail(testUser.getEmail());
    }

    @DisplayName("로그인 실패 - 일치하지 않는 비밀번호")
    @Test
    void loginTest_Fail_NotPassword() {
        // given
        given(userRepository.findByEmail(testUser.getEmail())).willReturn(Optional.of(testUser));

        // when && then
        assertThatCode(() -> authService.login(request))
                .isInstanceOf(CustomException.class)
                .hasMessageContaining("로그인 정보에 해당하는 유저가 존재하지 않습니다.");
        then(userRepository).should(times(1)).findByEmail(testUser.getEmail());
        then(passwordEncoder).should(times(1)).matches(request.password(), testUser.getPassword());
    }
}
