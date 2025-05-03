package org.example.springauthpractice.auth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.auth.application.OAuth2LoginSuccessHandlerFactory;
import org.example.springauthpractice.auth.exception.CustomAccessDeniedHandler;
import org.example.springauthpractice.auth.exception.CustomAuthenticationEntryPoint;
import org.example.springauthpractice.auth.filter.JwtAuthFilter;
import org.example.springauthpractice.auth.util.JwtUtil;
import org.example.springauthpractice.user.application.OAuth2UserServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final OAuth2UserServiceFactory oAuth2UserServiceFactory;
    private final OAuth2LoginSuccessHandlerFactory successHandlerFactory;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // CORS 설정
        http.cors(cors -> cors.configurationSource(configurationSource()));

        // CSRF 비활성화 (보통 REST API는 CSRF 비활성화)
        http.csrf(AbstractHttpConfigurer::disable);

        // Form Login 비활성화 (프론트에서 로그인 처리할 경우 주로 비활성화)
        http.formLogin(AbstractHttpConfigurer::disable);

        // HTTP Basic 인증 비활성화 (JWT 사용 시 필요 없음)
        http.httpBasic(AbstractHttpConfigurer::disable);

        // 경로별 인가 작업
        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/oauth2/**", "/auth/login/kakao/**").permitAll()
                .anyRequest().authenticated());

        // 세션 설정 : STATELESS
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // JWT 필터 추가
        http.addFilterBefore(new JwtAuthFilter(jwtUtil, objectMapper), UsernamePasswordAuthenticationFilter.class);

        // Exception handler 추가
        http.exceptionHandling((exceptionHandling) -> exceptionHandling
                .accessDeniedHandler(new CustomAccessDeniedHandler(objectMapper))
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)));

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfo -> userInfo.userService(oAuth2UserServiceFactory::loadUser))
                .successHandler((request, response, authentication) -> {
                    OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;
                    String provider = oAuth2Token.getAuthorizedClientRegistrationId();

                    AuthenticationSuccessHandler handler = successHandlerFactory.getHandler(provider);
                    handler.onAuthenticationSuccess(request, response, authentication);
                })
        );

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowedOrigins(Arrays.asList("https://", "http://localhost:5173", "https://","https://"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("ACCESS_TOKEN");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
