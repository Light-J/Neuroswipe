package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServices implements AdminServicesStatic {

    @Autowired
    AccountRepositoryStatic accountRepository;

    public boolean removeUser(Long userId){
        return accountRepository.removeUser(userId);
    }

    public Long removeUserResponses(Long userId){
        return accountRepository.removeUserResponses(userId);
    }

}
