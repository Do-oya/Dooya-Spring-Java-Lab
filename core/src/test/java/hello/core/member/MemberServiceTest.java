package hello.core.member;

import hello.core.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class MemberServiceTest {

    MemberService memberService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    @DisplayName("회원가입 테스트")
    public void test() {
        // given
        Member member = new Member(1L, "dooya", Grade.VIP);

        // when
        memberService.join(member);
        Member findMember = memberService.findMember(member.getId());


        // then
        assertThat(findMember).isEqualTo(member);
    }
}
