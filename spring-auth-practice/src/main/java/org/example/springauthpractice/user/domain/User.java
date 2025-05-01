package org.example.springauthpractice.user.domain;

import jakarta.persistence.*;
import lombok.*;
import org.example.springauthpractice.user.application.UserSignupRequest;

@Table(name = "users")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User signup(String email, String name, String password) {
        return User.builder()
                .email(email)
                .name(name)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
