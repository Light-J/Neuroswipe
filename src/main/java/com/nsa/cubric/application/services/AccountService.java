package com.nsa.cubric.application.services;

import com.nsa.cubric.application.configurators.MyUserPrincipal;
import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.accountUtils.EmailExistsException;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.nsa.cubric.application.configurators.WebSecurityConfig.passwordEncoder;

@Service
public class AccountService implements AccountServiceStatic {

    private AccountRepositoryStatic accountRepository;

    @Autowired
    public AccountService(AccountRepositoryStatic aRepo){
        accountRepository = aRepo;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Transactional
    @Override
    public Account registerNewUser(AccountDTO account) throws EmailExistsException {
        if(emailExist(account.getEmail())){
                throw new EmailExistsException("Account already exists");

        }
        account.setPassword(passwordEncoder().encode(account.getPassword()));
        accountRepository.insertNewAccount(account);

        return accountRepository.findByEmail(account.getEmail());
    }

    @Transactional
    @Override
    public Account loginUser(AccountDTO account) throws Exception{
        Account resultAccount = accountRepository.findByEmail(account.getEmail());

        if (resultAccount == null){
            System.out.println("Account with this email could not be found");
            throw new Exception("Account with this email could not be found");
        }

        if (!BCrypt.checkpw(account.getPassword(), resultAccount.getPassword())){
            System.out.println("Password is incorrect");
            throw new Exception("Password is incorrect");
        }

        return resultAccount;
    }

    //true if email exists in DB
    private boolean emailExist(String email) {
        return (accountRepository.findByEmail(email) != null);



    }
}
