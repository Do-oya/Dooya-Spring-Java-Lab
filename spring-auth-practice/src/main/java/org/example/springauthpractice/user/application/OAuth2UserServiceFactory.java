package org.example.springauthpractice.user.application;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OAuth2UserServiceFactory {

    private final KakaoOAuth2ServiceImpl kakaoOAuth2ServiceImpl;

    public OAuth2User loadUser(OAuth2UserRequest request) {
        String registrationId = request.getClientRegistration().getRegistrationId();

        if ("kakao".equalsIgnoreCase(registrationId)) {
            return kakaoOAuth2ServiceImpl.loadUser(request);
        }

        throw new IllegalStateException("지원하지 않는 OAuth2 Provider: " + registrationId);
    }
}
