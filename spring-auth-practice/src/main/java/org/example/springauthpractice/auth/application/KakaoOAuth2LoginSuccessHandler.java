package org.example.springauthpractice.auth.application;

import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.common.exception.CustomException;
import org.example.springauthpractice.common.exception.ErrorCode;
import org.example.springauthpractice.user.domain.Role;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class KakaoOAuth2LoginSuccessHandler extends AbstractOAuth2LoginSuccessHandler {

    private final UserRepository userRepository;

    public KakaoOAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        super(jwtUtil, userRepository);
        this.userRepository = userRepository;
    }

    @Override
    protected String getEmail(OAuth2User oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        return (String) kakaoAccount.get("email");
    }

    @Override
    protected Long getUserId(OAuth2User oAuth2User) {
        User user = getUserData(oAuth2User);

        return user.getId();
    }

    @Override
    protected Role getRole(OAuth2User oAuth2User) {
        User user = getUserData(oAuth2User);

        return user.getRole();
    }

    private User getUserData(OAuth2User oAuth2User) {
        String email = getEmail(oAuth2User);
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
