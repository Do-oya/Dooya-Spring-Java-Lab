package org.example.springauthpractice.user.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.springauthpractice.user.application.UserSignupRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
                .andExpect(jsonPath("$.name").value(request.name()))
                .andExpect(jsonPath("$.password").value(request.password()));
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
}
