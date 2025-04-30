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

    public static User signup(UserSignupRequest request) {
        return User.builder()
                .email(request.email())
                .name(request.name())
                .password(request.password())
                .role(Role.USER)
                .build();
    }
}
