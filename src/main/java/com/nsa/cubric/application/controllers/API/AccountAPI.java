package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.PasswordResetToken;
import com.nsa.cubric.application.services.AccountService;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/account")
public class AccountAPI {

    private AccountService accountService;

    @Autowired
    public AccountAPI(AccountService accountService){
        this.accountService = accountService;
    }

    @RequestMapping(value = "/update/email", method = RequestMethod.PUT)
    public ResponseEntity updateEmail(@RequestParam("newEmail") String newEmail){
        HttpHeaders headers = new HttpHeaders();
        if(accountService.checkEmailFormat(newEmail)) {
            if (accountService.emailExist(newEmail)) {
                headers.add("emailExists", "true");
            } else {
                boolean success = accountService.updateEmail(newEmail);
                if (success) {
                    headers.add("updated", "true");

                } else {
                    headers.add("updated", "false");
                }
            }
        } else {
            headers.add("invalidFormat", "true");
        }
        return new ResponseEntity(headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/disable", method = RequestMethod.POST)
    public ResponseEntity disableAccount(){
        HttpHeaders headers = new HttpHeaders();
        boolean success = accountService.disableUser();
        if(success){
            headers.add("disabled", "true");
        } else {
            headers.add("disabled", "false");
        }

        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity deleteAccount(){
        HttpHeaders headers = new HttpHeaders();
        boolean success = accountService.deleteUser();
        if(success){
            headers.add("deleted", "true");
        } else {
            headers.add("deleted", "false");
        }

        return new ResponseEntity(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/reset/request", method = RequestMethod.POST)
    public boolean resetRequest(@RequestParam String email, HttpServletRequest request){

        Account user = accountService.getAccountByEmail(email);
        try{
            if(user == null){
                throw new UsernameNotFoundException(email);
            }
            accountService.removeExistingTokens(email);
            PasswordResetToken token = accountService.createResetToken(email);
            accountService.sendResetToken(token, request.getHeader("host"));
        } catch (Exception e){
            return false;
        }

        return true;
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public boolean updatePassword(@RequestParam("password") String password,
                                  @RequestParam("matchingpassword") String matchingPassword){

        if(password.equals(matchingPassword) && accountService.checkPasswordStrength(password) > 1){
            Account user = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            accountService.changeUserPassword(password, user.getId());
            return true;
        } else {
            return false;
        }
    }



}
