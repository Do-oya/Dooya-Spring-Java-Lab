package org.example.springauthpractice.auth.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.auth.application.AuthService;
import org.example.springauthpractice.auth.application.LoginRequest;
import org.example.springauthpractice.auth.application.LoginResponse;
import org.example.springauthpractice.auth.util.CookieUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Value("${cookie.valid-time}")
    private long COOKIE_VALID_TIME;

    @Value("${cookie.name}")
    private String COOKIE_NAME;

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        ResponseCookie cookie = CookieUtil.createCookie(COOKIE_NAME, response.accessToken(), COOKIE_VALID_TIME);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(response);
    }
}
