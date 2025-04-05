package org.example.user.domain;

import java.util.Objects;

public class User {
    private final Long id;
    private final UserInfo info;
    private final UserRelationCounter followingCounter;
    private final UserRelationCounter followerCounter;

    public User(Long id, UserInfo info) {
        this.id = id;
        this.info = info;
        this.followingCounter = new UserRelationCounter();
        this.followerCounter = new UserRelationCounter();
    }

    public void follow(User targetUser) {
        if (targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.increase();
        targetUser.increaseFollowerCount();
    }

    public void unfollow(User targetUser) {
        if (targetUser.equals(this)) {
            throw new IllegalArgumentException();
        }

        followingCounter.decrease();
        targetUser.decreaseFollowerCount();
    }

    private void increaseFollowerCount() {
        followingCounter.increase();
    }

    private void decreaseFollowerCount() {
        followingCounter.decrease();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
