package com.nsa.cubric.application.services;

import com.nsa.cubric.application.configurators.MyUserPrincipal;
import com.nsa.cubric.application.domain.Account;
//import com.nsa.cubric.application.domain.User;
import com.nsa.cubric.application.repositories.AccountRepository;
//import com.nsa.cubric.application.repositories.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Account user = accountRepository.getAccountByEmail(email);
        if (user == null) {
            System.out.println("EMAIL NOT FOUND");
            throw new UsernameNotFoundException(email);
        } else {
            System.out.println("User = " + user);

            List<String> userRoles = Collections.singletonList(user.getRole());
            System.out.println("userRoles = " + userRoles.toString());
            return new MyUserPrincipal(user, userRoles);
        }
    }
}

