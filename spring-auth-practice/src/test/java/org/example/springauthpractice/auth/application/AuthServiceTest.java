package org.example.springauthpractice.auth.application;

import org.example.springauthpractice.auth.presentation.LoginResponse;
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

    @DisplayName("로그인 성공 테스트")
    @Test
    void loginTest() {
        // given
        User mockUser = new User(1L, "testId", "testPassword");
        given(userJpaRepository.findByName(mockUser.getName())).willReturn(mockUser);

        UserRepository userRepository = new UserRepositoryImpl(userJpaRepository);
        AuthService authService = new AuthServiceImpl(userRepository);

        LoginRequest request = new LoginRequest("testId", "testPassword");

        // when
        LoginResponse response = authService.login(request);

        // then
        assertThat(mockUser.getName()).isEqualTo(response.name());
        then(userJpaRepository).should(times(1)).findByName(mockUser.getName());
    }
}
