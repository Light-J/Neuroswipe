package com.nsa.cubric.application.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountControllerTest {

    @Autowired
    MockMvc mvc;

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
        this.mvc.perform(post("/registration/account")
                .param("email", "test")
                .param("password", "Password")
                .param("matchingPassword", "Password"))
                .andExpect(model().hasErrors())
                .andExpect(status().isOk());
    }

    @Test
    public void submitRegistrationValid() throws Exception{
        this.mvc.perform(post("/registration/account")
                .param("email", "test@nsa.com")
                .param("password", "Password")
                .param("matchingPassword", "Password"))
                .andExpect(model().hasNoErrors())
                .andExpect(status().isOk());
    }

}
