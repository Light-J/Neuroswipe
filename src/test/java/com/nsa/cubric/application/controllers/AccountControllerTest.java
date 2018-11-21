package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
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

    @Autowired
    MockMvc mvc;

    @MockBean
    AccountService accountService;



    @Before
    public void setupBasicAccountDTO() {
        testAccount = new AccountDTO();
        testAccount.setEmail("test@nsa.com");
        testAccount.setPassword("Password");
        testAccount.setMatchingPassword("Password");
    }



    @Test
    public void submitRegistrationInvalidPasswords() throws Exception{
        this.mvc.perform(post("/registration/account")
                .param("email", "test@nsa.com")
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
        Account validAccount = new Account(1L, "test@nsa", "pass", "user");

        given(accountService.registerNewUserAccount(testAccount)).willReturn(validAccount);

        this.mvc.perform(post("/registration/account")

                .param("email", testAccount.getEmail())
                .param("password", testAccount.getPassword())
                .param("matchingPassword", testAccount.getMatchingPassword()))
                .andExpect(model().hasNoErrors())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void submitDetailsInvalidPostCode() throws Exception{

        this.mvc.perform(post("/registration/profile")

                .param("postcode", "AAAA"))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

}
