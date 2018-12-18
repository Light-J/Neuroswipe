package com.nsa.cubric.application.repositories;


import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.Profile;
import com.nsa.cubric.application.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJdbc
public class AccountRepositoryTest {

    @Autowired
    private AccountRepositoryStatic accountRepository;

    AccountDTO accountDTO;

    static AccountDTO insertBasicAccount(AccountRepositoryStatic accountRepository){
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail("user@test");
        accountDTO.setPassword("pass");
        accountDTO.setMatchingPassword("pass");
        accountRepository.insertNewAccount(accountDTO);
        return accountDTO;
    }

    @Before
    public void setupBasicDetails(){
        //Insert this test data to use before each test
        //This email is invalid so couldn't exist in the database under normal conditions
        accountDTO = insertBasicAccount(accountRepository);
    }

    @After
    public void removeBasicDetails(){
        //Remove the test data so they are repeatable
        accountRepository.removeUser(accountRepository.getAccountByEmail(accountDTO.getEmail()).getId());
    }


    @Test
    public void insertUserAccountAndCheckDetails() throws Exception{
        Account retrievedAccount = accountRepository.getAccountByEmail("user@test");
        Profile retrievedProfile = accountRepository.getProfileByEmail("user@test");
        assertEquals("user@test", retrievedAccount.getEmail());
        assertEquals(retrievedAccount.getId().longValue(), retrievedProfile.getUserAccountId());
    }


    @Test
    public void updateUserRole() throws Exception{
        Long accountIdToUpdate = accountRepository.getAccountByEmail("user@test").getId();
        accountRepository.updateUserRole(accountIdToUpdate, "admin");
        assertEquals("admin", accountRepository.getAccountById(accountIdToUpdate).getRole());
    }
}