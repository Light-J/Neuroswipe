package com.nsa.cubric.application.services;

import com.nsa.cubric.application.configurators.MyUserPrincipal;
import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
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
    public Account registerNewUserAccount(AccountDTO account) throws EmailExistsException {
        if(emailExist(account.getEmail())){
                throw new EmailExistsException("Account already exists");

        }
        account.setPassword(passwordEncoder().encode(account.getPassword()));
        accountRepository.insertNewAccount(account);

        return accountRepository.findByEmail(account.getEmail());
    }

    @Override
    public void registerNewUserProfile(ProfileDTO profile){
        accountRepository.insertNewProfile(profile);
    }

    @Override
    public Account findByEmail(String email){
        return accountRepository.findByEmail(email);
    }




    //true if email exists in DB
    private boolean emailExist(String email) {
        return (accountRepository.findByEmail(email) != null);

    }


}
