package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServicesStatic implements AdminServices {

    private AccountRepository accountRepository;

    @Autowired
    public AdminServicesStatic(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public boolean removeUser(Long userId){
        return accountRepository.removeUser(userId);
    }

    @Override
    public Integer removeUserResponses(Long userId){
        return accountRepository.removeUserResponses(userId);
    }

    @Override
    public boolean updateUserRole(Long userId, String role){
        return accountRepository.updateUserRole(userId, role);
    }


}
