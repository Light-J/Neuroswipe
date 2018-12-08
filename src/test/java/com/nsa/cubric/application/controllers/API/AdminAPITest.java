package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.AdminServicesStatic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AdminAPITest {


    Account testAccount;

    @Autowired
    MockMvc mvc;

    @MockBean
    AdminServicesStatic adminServices;

    @MockBean
    AccountServiceStatic accountService;

    @Before
    public void setupTest() {
        testAccount = new Account(1L, "test@account.com", "pass", "user");
        given(accountService.findByEmail("test@account.com")).willReturn(testAccount);
    }



    @Test
    public void removeUserValidTest() throws Exception{
        given(adminServices.removeUser(1L)).willReturn(true);
        mvc.perform(post("/removeUser")
                .param("user_to_remove", "test@account.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void removeUserInvalidTest() throws Exception{
        given(adminServices.removeUser(2L)).willReturn(false);

        mvc.perform(post("/removeUser")
                .param("user_to_remove", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void removeUserResponses() throws Exception{
        given(adminServices.removeUserResponses(1L)).willReturn(0L);
        mvc.perform(post("/removeUserResponses")
                .param("user_to_remove_responses", "test@account.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}
