package org.example.springauthpractice.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.example.springauthpractice.auth.application.LoginResponse;
import org.example.springauthpractice.auth.domain.LoginUser;
import org.example.springauthpractice.user.application.UserResponse;
import org.example.springauthpractice.user.application.UserService;
import org.example.springauthpractice.user.application.UserSignupRequest;
import org.example.springauthpractice.user.application.UserSignupResponse;
import org.example.springauthpractice.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest request) {
        User user = userService.signupUser(request);
        UserSignupResponse response = new UserSignupResponse(user.getEmail(), user.getName(), user.getPassword());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<UserResponse> getLoginResponse(@AuthenticationPrincipal LoginUser loginUser) {
        String email = loginUser.getUsername();
        User user = userService.findByToken(email);
        UserResponse response = new UserResponse(user.getId(), user.getEmail(), user.getName(), user.getRole());
        return ResponseEntity.ok(response);
    }
}
