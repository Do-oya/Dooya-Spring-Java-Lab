package org.example.springauthpractice.user.presentation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.user.application.UserService;
import org.example.springauthpractice.user.application.UserSignupRequest;
import org.example.springauthpractice.user.application.UserSignupResponse;
import org.example.springauthpractice.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponse> signup(@Valid @RequestBody UserSignupRequest request) {
        User user = userService.signupUser(request);
        UserSignupResponse response = new UserSignupResponse(user.getName(), user.getPassword());
        return ResponseEntity.ok(response);
    }
}
