package org.example.fctdd.member.api;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Member {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    protected Member() {}

    public Member(String id, MemberStatus status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
