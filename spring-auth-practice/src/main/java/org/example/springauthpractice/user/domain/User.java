package org.example.springauthpractice.user.domain;

import jakarta.persistence.*;
import lombok.*;

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
    private String name;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static User signup(String name, String password) {
        return User.builder()
                .name(name)
                .password(password)
                .role(Role.USER)
                .build();
    }
}
