package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LoggedUserService {

    private AccountRepository accountRepository;

    @Autowired
    public LoggedUserService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    public String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return authentication.getName();
        } else {
            return null;
        }
    }

    public String getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Collection currentUserRoles = authentication.getAuthorities();
            return currentUserRoles.iterator().next().toString();
        } else {
            return null;
        }
    }


    public Long getUserProfileId(){
        return accountRepository.getProfileByEmail(getUsername()).getId();
    }
}
