package com.nsa.cubric.application.services;

import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.BDDMockito.given;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountServicesTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepositoryStatic accountRepositoryStatic;

    @Test(expected = EmailExistsException.class)
    public void checkEmailIsUnique() throws Exception{
        AccountDto accountToAdd = new AccountDto();
        accountToAdd.setEmail("test@nsa.com");

        Account alreadyExists = new Account(1L, "test@nsa.com", "pass", "user");
        given(accountRepositoryStatic.getAccountByEmail("test@nsa.com")).willReturn(alreadyExists);

        accountService.registerNewUserAccount(accountToAdd);
    }
}
