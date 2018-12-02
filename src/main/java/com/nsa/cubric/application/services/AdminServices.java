package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminServices implements AdminServicesStatic {

    @Autowired
    AccountRepositoryStatic accountRepository;

    public void removeUser(Integer userId){
        accountRepository.removeUser(userId);
    }

    public void removeUserResponses(Integer userId){
    }

}
