package com.nsa.cubric.application.repositories;


import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
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
public class AccountRepositoryTest {

    @Autowired
    private AccountRepositoryStatic accountRepository;

    AccountDTO accountDTO;

    @Before
    public void setupBasicDetails(){
        accountDTO = new AccountDTO();
        accountDTO.setEmail("user@test");
        accountDTO.setPassword("pass");
        accountDTO.setMatchingPassword("pass");
        accountRepository.insertNewAccount(accountDTO);
    }

    @Test
    public void insertUserAccountAndCheckDetails() throws Exception{
        Account retrievedAccount = accountRepository.getAccountByEmail("user@test");
        assertEquals("user@test", retrievedAccount.getEmail());
    }

    @Test
    public void updateUserRole() throws Exception{
        Long accountIdToUpdate = accountRepository.getAccountByEmail("user@test").getId();
        accountRepository.updateUserRole(accountIdToUpdate, "admin");
        assertEquals("admin", accountRepository.getAccountById(accountIdToUpdate).getRole());
    }
}