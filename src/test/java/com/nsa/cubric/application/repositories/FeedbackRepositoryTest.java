package com.nsa.cubric.application.repositories;


import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureJdbc
public class FeedbackRepositoryTest {

    @Autowired
    private FeedbackRepositoryStatic feedbackRepository;

    @Autowired
    private AccountRepositoryStatic accountRepository;

    private AccountDTO accountDTO;

    @Before
    public void setupBasicDetails(){
        //Insert this test data to use before each test
        //This email is invalid so couldn't exist in the database under normal conditions
        accountDTO = new AccountDTO();
        accountDTO.setEmail("user@test");
        accountDTO.setPassword("pass");
        accountDTO.setMatchingPassword("pass");
        accountRepository.insertNewAccount(accountDTO);
    }

    @After
    public void removeBasicDetails(){
        //Remove the test data so they are repeatable
        accountRepository.removeUser(accountRepository.getAccountByEmail("user@test").getId());
    }



}