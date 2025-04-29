package org.example.springauthpractice.user.application;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @DisplayName("회원 가입 테스트")
    @Test
    void signupTest() {
        // given
        UserRepository userRepository = new UserRepositoryImpl(userJpaRepository);
        UserService userService = new UserServiceImpl(userRepository);

        UserSignupRequest request = new UserSignupRequest("testId", "testPassword");
        User mockUser = new User(1L, "testId", "testPassword");

        given(userJpaRepository.save(any(User.class))).willReturn(mockUser);

        // when
        User user = userService.signupUser(request);

        // then
        assertThat(user.getName()).isEqualTo("testId");
        assertThat(user.getPassword()).isEqualTo("testPassword");
        then(userJpaRepository).should(times(1)).save(any(User.class));
    }
}