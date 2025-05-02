package org.example.springauthpractice.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;
import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.fixture.UserFixture;
import org.example.springauthpractice.user.application.UserSignupRequest;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.infrastructure.UserRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:init.sql")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @DisplayName("유저 회원가입 성공")
    @Test
    void userSingUp_success() throws Exception {
        // given
        UserSignupRequest request = new UserSignupRequest("testEmail", "testId", "testPassword");

        // when && then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(request.email()))
                .andExpect(jsonPath("$.name").value(request.name()));
    }

    @DisplayName("유저 회원가입 실패 - 이메일이 공란일때")
    @Test
    void userSingUp_fail_noEmail() throws Exception {
        // given
        UserSignupRequest request = new UserSignupRequest("", "testId", "testPassword");

        // when && then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationErrors[0].field").value("email"))
                .andExpect(jsonPath("$.validationErrors[0].message").value("이메일은 비어 있을 수 없습니다"));
    }

    @DisplayName("유저 회원가입 실패 - 이름이 공란일때")
    @Test
    void userSingUp_fail_noUser() throws Exception {
        // given
        UserSignupRequest request = new UserSignupRequest("testEmail", "", "testPassword");

        // when && then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationErrors[0].field").value("name"))
                .andExpect(jsonPath("$.validationErrors[0].message").value("이름은 비어 있을 수 없습니다"));
    }

    @DisplayName("유저 회원가입 실패 - 비밀번호가 공란일때")
    @Test
    void userSingUp_fail_noPassword() throws Exception {
        // given
        UserSignupRequest request = new UserSignupRequest("testEmail", "testId", "");

        // when && then
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(jsonPath("$.validationErrors[0].field").value("password"))
                .andExpect(jsonPath("$.validationErrors[0].message").value("비밀번호는 비어 있을 수 없습니다"));
    }

    @DisplayName("토큰으로 유저 조회 성공")
    @Test
    void userFind_Success() throws Exception {
        // given
        User testUser = userRepositoryImpl.save(UserFixture.testUser(passwordEncoder));
        String testToken = generateTestToken(testUser);

        // when && then
        mockMvc.perform(get("/users/my")
                        .cookie(new Cookie("Authorization", testToken))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(testUser.getEmail()))
                .andExpect(jsonPath("$.name").value(testUser.getName()))
                .andExpect(jsonPath("$.role").value(testUser.getRole().name()));
    }

    private String generateTestToken(User user) {
        return jwtUtil.createAccessToken(user.getId(), user.getEmail(), user.getRole());
    }
}
