package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class LoginControllerTest {

    AccountDTO testAccount;
    @Autowired
    MockMvc mmvc;
    @MockBean
    AccountService accountService;

    @Before
    public void makeTestAccount() {
        testAccount = new AccountDTO();
        testAccount.setEmail("test@test.test");
        testAccount.setPassword("pass");
        testAccount.setMatchingPassword("pass");
    }

    @Test
    public void testWrongPassword() throws Exception{
        this.mmvc.perform(post("/login")
                .param("username", "test@test.test")
                .param("password", "wrongPass"))
                //.andExpect(model().hasErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testWrongEmail() throws Exception{
        this.mmvc.perform(post("/login")
                .param("username", "test@test.commmm")
                .param("password", "pass"))
                //.andExpect(model().hasErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testCorrectCredentials() throws Exception{
        this.mmvc.perform(post("/login")
                .param("username", "test@test.test")
                .param("password", "pass"))
           //     .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void testUnauthorisedAccess() throws Exception{
        this.mmvc.perform(post("/practice/"))
                //     .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }



}
