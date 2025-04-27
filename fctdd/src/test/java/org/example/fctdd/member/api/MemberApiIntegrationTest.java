package org.example.fctdd.member.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql("classpath:init.sql")
public class MemberApiIntegrationTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MockMvc mvc;

    @Test
    void confirm() throws Exception {
        // arrange
        memberRepository.save(new Member("id", MemberStatus.WAITING));

        // act
        mvc.perform(post("/members/{id}/confirm", "id"))
                .andExpect(status().isOk());

        // assert
        Member m = memberRepository.findById("id")
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));
        assertThat(m.getStatus()).isEqualTo(MemberStatus.ACTIVE);
    }
}
