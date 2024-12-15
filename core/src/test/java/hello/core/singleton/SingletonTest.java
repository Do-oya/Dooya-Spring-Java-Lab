package hello.core.singleton;

import hello.core.AppConfig;
import hello.core.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    public void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회
        MemberService memberService1 = appConfig.memberService();
        // 2. 조회
        MemberService memberService2 = appConfig.memberService();
        // 3. 참조값 확인
        System.out.println(memberService1);
        System.out.println(memberService2);

        // !=
        assertThat(memberService1).isNotSameAs(memberService2);
    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    public void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        // 1. 조회
        MemberService memberService1 = ac.getBean(MemberService.class);
        // 2. 조회
        MemberService memberService2 = ac.getBean(MemberService.class);
        // 3. 참조값 확인
        System.out.println(memberService1);
        System.out.println(memberService2);

        // ==
        assertThat(memberService1).isSameAs(memberService2);
    }
}
