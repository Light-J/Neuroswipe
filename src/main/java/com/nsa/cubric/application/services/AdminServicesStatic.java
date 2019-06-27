package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServicesStatic implements AdminServices {

    private AccountRepository accountRepository;
    private LoggedUserService loggedUserService;

    @Autowired
    public AdminServicesStatic(AccountRepository accountRepository, LoggedUserService loggedUserService){
        this.accountRepository = accountRepository;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public boolean removeUser(Long userId){
        Long loggedInUserId = accountRepository.getAccountByEmail(loggedUserService.getUsername()).getId();
        if(loggedInUserId.equals(userId)){
            return false;
        }

        return accountRepository.removeUser(userId);
    }

    @Override
    public Integer removeUserResponses(Long userId){
        return accountRepository.removeUserResponses(userId);
    }

    @Override
    public boolean updateUserRole(Long userId, String role){
        Long loggedInUserId = accountRepository.getAccountByEmail(loggedUserService.getUsername()).getId();
        Long defaultAdminId = accountRepository.getAccountByEmail("default@admin").getId();
        if(loggedInUserId.equals(userId) || defaultAdminId.equals(userId)){
            return false;
        }
        return accountRepository.updateUserRole(userId, role);
    }

    @Override
    public boolean updateUserDisabledStatus(Long userId, String disabled){
        Long defaultAdminId = accountRepository.getAccountByEmail("default@admin").getId();
        if(defaultAdminId.equals(userId)){
            return false;
        }
        boolean boolDisabled;
        try{
            boolDisabled = Boolean.parseBoolean(disabled);
        } catch (Exception e) {
            return false;
        }
        return accountRepository.updateUserDisabledStatus(userId, boolDisabled);
    }

}
