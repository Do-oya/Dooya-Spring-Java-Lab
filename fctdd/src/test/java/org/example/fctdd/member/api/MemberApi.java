package org.example.fctdd.member.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberApi {

    private final ConfirmMemberService confirmMemberService;

    public MemberApi(ConfirmMemberService confirmMemberService) {
        this.confirmMemberService = confirmMemberService;
    }

    @PostMapping("members/{id}/confirm")
    public ResponseEntity<?> confirm(@PathVariable("id") String id) {
        confirmMemberService.confirm(id);
        return ResponseEntity.ok("OK");
    }
}
