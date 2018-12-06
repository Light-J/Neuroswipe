package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServices implements AdminServicesStatic {

    @Autowired
    AccountRepositoryStatic accountRepository;

    public boolean removeUser(Integer userId){
        return accountRepository.removeUser(userId);
    }

    public Integer removeUserResponses(Integer userId){
        return accountRepository.removeUserResponses(userId);
    }

}