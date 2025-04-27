package org.example.fctdd.member.api;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberApi.class)
public class MemberApiTest {
    @Autowired
    private MockMvc mvc;
    @MockitoBean
    private ConfirmMemberService confirmMemberService;

    @Test
    void shouldCallService() throws Exception {
        // when
        mvc.perform(post("/members/{id}/confirm", "id"))
                .andExpect(status().isOk());

        // then
        BDDMockito.then(confirmMemberService)
                .should()
                .confirm("id");
    }
}
