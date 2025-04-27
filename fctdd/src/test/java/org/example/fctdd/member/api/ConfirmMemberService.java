package org.example.fctdd.member.api;

import org.springframework.stereotype.Service;

@Service
public class ConfirmMemberService {
    public void confirm(String id) {
        throw new MemberNotFoundException();
    }
}
