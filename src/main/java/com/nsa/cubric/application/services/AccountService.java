package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.EmailExistsException;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService implements AccountServiceStatic {

    private AccountRepositoryStatic accountRepository;

    @Autowired
    public AccountService(AccountRepositoryStatic aRepo){
        accountRepository = aRepo;
    }

    @Transactional
    @Override
    public Account registerNewUser(AccountDTO account) throws EmailExistsException {
        if(emailExist(account.getEmail())){
                throw new EmailExistsException("Account already exists");
        }
        accountRepository.insertNewAccount(account);

        return accountRepository.findByEmail(account.getEmail());
    }


    //true if email exists in DB
    private boolean emailExist(String email) {
        return (accountRepository.findByEmail(email) != null);



    }
}
