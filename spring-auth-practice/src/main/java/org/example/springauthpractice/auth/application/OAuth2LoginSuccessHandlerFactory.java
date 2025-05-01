package org.example.springauthpractice.auth.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandlerFactory {

    private final KakaoOAuth2LoginSuccessHandler kakaoOAuth2LoginSuccessHandler;

    public AuthenticationSuccessHandler getHandler(String provider) {
        if ("KAKAO".equalsIgnoreCase(provider)) {
            return kakaoOAuth2LoginSuccessHandler;
        }

        throw new IllegalArgumentException("지원하지 않는 OAuth2 Provider: " + provider);
    }
}
