package com.nsa.cubric.application.services;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

public class LoggedUserService {

    public static String getUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        } else {
            return null;
        }
    }

    public static String getUserRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Collection currentUserRoles = authentication.getAuthorities();
            return currentUserRoles.iterator().next().toString();
        } else {
            return null;
        }
    }
}
