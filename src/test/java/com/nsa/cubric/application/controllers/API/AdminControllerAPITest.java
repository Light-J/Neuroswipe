package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.AdminServices;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AdminControllerAPITest {


    Account testAccount;

    @Autowired
    MockMvc mvc;

    @MockBean
    AdminServices adminServices;

    @MockBean
    AccountService accountService;

    @Before
    public void setupTest() {
        testAccount = new Account(1L, "test@account.com", "pass", "user");
        given(accountService.getAccountByEmail("test@account.com")).willReturn(testAccount);
    }



    @Test
    @WithMockUser(username = "user@test.com", authorities = { "admin" })
    public void removeUserValidTest() throws Exception{
        given(adminServices.removeUser(1L)).willReturn(true);
        mvc.perform(post("/api/admin/removeUser")
                .param("userToRemove", "test@account.com")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser(username = "user@test.com", authorities = { "admin" })
    public void removeUserInvalidTest() throws Exception{
        given(adminServices.removeUser(2L)).willReturn(false);

        mvc.perform(post("/api/admin/removeUser")
                .param("userToRemove", "2")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @WithMockUser(username = "user@test.com", authorities = { "admin" })
    public void removeUserResponses() throws Exception{
        given(adminServices.removeUserResponses(1L)).willReturn(0);
        mvc.perform(post("/api/admin/removeUserResponses")
                .param("userToRemoveResponses", "test@account.com")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }
}
