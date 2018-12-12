package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServices implements AdminServicesStatic {

    @Autowired
    AccountRepositoryStatic accountRepository;

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
