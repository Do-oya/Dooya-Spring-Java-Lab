package org.example.springauthpractice.user.application;

import org.example.springauthpractice.fixture.UserFixture;
import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @DisplayName("회원가입 성공 테스트")
    @Test
    void signupTest_Success() {
        // given
        UserSignupRequest request = UserSignupRequest.builder()
                .email("testEmail")
                .name("testName")
                .password("testPassword")
                .build();

        given(passwordEncoder.encode(request.password())).willReturn("encodedPassword");
        User testUser = User.signup(request.email(), request.name(), "encodedPassword");
        given(userRepository.save(any(User.class))).willReturn(testUser);

        // when
        User user = userService.signupUser(request);

        // then
        assertThat(user.getEmail()).isEqualTo("testEmail");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        then(userRepository).should(times(1)).save(any(User.class));
    }

    @DisplayName("토큰 조회 성공 테스트")
    @Test
    void tokenFindTest_Success() {
        // given
        User testUser = UserFixture.testUser(passwordEncoder);
        given(userRepository.findByEmail("testEmail"))
                .willReturn(Optional.of(testUser));

        // when
        User user = userService.findByToken("testEmail");
        UserResponse response = new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());

        // then
        assertThat("testEmail").isEqualTo(response.email());
        assertThat("testName").isEqualTo(response.name());
        assertThat(Role.USER).isEqualTo(response.role());
        then(userRepository).should(times(1)).findByEmail("testEmail");
    }
}