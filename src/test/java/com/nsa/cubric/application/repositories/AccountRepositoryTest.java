package com.nsa.cubric.application.repositories;


import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.domain.Account;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureJdbc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureJdbc
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    AccountDto accountDto;

    static AccountDto insertBasicAccount(AccountRepository accountRepository){
        AccountDto accountDto = new AccountDto();
        accountDto.setEmail("user@test");
        accountDto.setPassword("pass");
        accountDto.setMatchingPassword("pass");
        accountRepository.insertNewAccount(accountDto);
        return accountDto;
    }

    @Before
    public void setupBasicDetails(){
        //Insert this test data to use before each test
        //This email is invalid so couldn't exist in the database under normal conditions
        accountDto = insertBasicAccount(accountRepository);
    }

    @After
    public void removeBasicDetails(){
        //Remove the test data so they are repeatable
        accountRepository.removeUser(accountRepository.getAccountByEmail(accountDto.getEmail()).getId());
    }


    @Test
    public void insertUserAccountAndCheckDetails() throws Exception{
        Account retrievedAccount = accountRepository.getAccountByEmail("user@test");
        ProfileDto retrievedProfileDto = accountRepository.getProfileByEmail("user@test");
        assertEquals("user@test", retrievedAccount.getEmail());
        assertEquals(retrievedAccount.getId().longValue(), retrievedProfileDto.getUserAccountId());
    }


    @Test
    public void updateUserRole() throws Exception{
        Long accountIdToUpdate = accountRepository.getAccountByEmail("user@test").getId();
        accountRepository.updateUserRole(accountIdToUpdate, "admin");
        assertEquals("admin", accountRepository.getAccountById(accountIdToUpdate).getRole());
    }
}