package org.example.springauthpractice.auth.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/success")
    public String success() {
        return "OAuth2 Login Success";
    }
}
