package org.example.springauthpractice.user.application;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class KakaoOAuth2ServiceImpl extends DefaultOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = extractEmail(attributes);
        String name = extractName(attributes);

        saveOrUpdateUser(email, name);

        return oAuth2User;
    }

    private String extractEmail(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.getOrDefault("kakao_account", Map.of());
        return (String) kakaoAccount.get("email");
    }

    private String extractName(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.getOrDefault("kakao_account", Map.of());
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.getOrDefault("profile", Map.of());
        return (String) profile.getOrDefault("nickname", "Unknown");
    }

    private void saveOrUpdateUser(String email, String name) {
        userRepository.findByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .email(email)
                    .name(name)
                    .password(null)
                    .role(Role.USER)
                    .build();
            return userRepository.save(user);
        });
    }
}
