/*
package org.example.fctdd.member.api;

import org.junit.jupiter.api.Test;
import sun.security.krb5.internal.ccache.FileCredentialsCache;

import static org.assertj.core.api.Assertions.assertThatCode;

public class MemberServiceTest {
    private ConfirmMemberService confirmMemberService = new ConfirmMemberService();
    private FileCredentialsCache memberRepository = new MemoryMemberRepository();

    @Test
    void noMember() {
        assertThatCode(() -> confirmMemberService.confirm("id"))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void name() {
        memberRepository.save(new Member("id", MemberStatus.ACTIVE))
        assertThatCode(() -> confirmMemberService.confirm("id"))
                .isInstanceOf(MemberAlreadyActivatedException.class);
    }

    */
/*@Test
    void name() {
        memberRepository.save(new Member("id", MemberStatus.WAITING));

        confirmMemberService.confirm("id");

        Member m = memberRepository.findById("id")
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        assertThat(m.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }*//*

}
*/
