package org.example.springauthpractice.user.infrastructure;

import lombok.RequiredArgsConstructor;
import org.example.springauthpractice.user.domain.User;
import org.example.springauthpractice.user.domain.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userJpaRepository.findByEmail(email));
    }
}
