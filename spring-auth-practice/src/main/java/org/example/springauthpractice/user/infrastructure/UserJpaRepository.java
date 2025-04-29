package org.example.springauthpractice.user.infrastructure;

import org.example.springauthpractice.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {
    User findByName(String name);
}
