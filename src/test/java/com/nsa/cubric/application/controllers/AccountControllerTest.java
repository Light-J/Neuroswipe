package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class AccountControllerTest {

    AccountDTO testAccount;

    Account validAccount;

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountService accountService;



    @Before
    public void setupBasicAccountDTO() {
        validAccount = new Account(1L, "test@user.com", "pass", "user");
        testAccount = new AccountDTO();
        testAccount.setEmail("test@user.com");
        testAccount.setPassword("Password");
        testAccount.setMatchingPassword("Password");
    }



    @Test
    public void submitRegistrationInvalidPasswords() throws Exception{
        this.mvc.perform(post("/registration/account")
                .param("email", "test@user.com")
                .param("password", "Password")
                .param("matchingPassword", "NotMatching"))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationInvalidEmail() throws Exception{
        given(accountService.registerNewUserAccount(testAccount)).willReturn(null);
        this.mvc.perform(post("/registration/account")
                .param("email", testAccount.getEmail())
                .param("password", testAccount.getPassword())
                .param("matchingPassword", testAccount.getMatchingPassword()))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationValid() throws Exception{

        given(accountService.registerNewUserAccount(testAccount)).willReturn(validAccount);

        this.mvc.perform(post("/registration/account")

                .param("email", testAccount.getEmail())
                .param("password", testAccount.getPassword())
                .param("matchingPassword", testAccount.getMatchingPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }
}
