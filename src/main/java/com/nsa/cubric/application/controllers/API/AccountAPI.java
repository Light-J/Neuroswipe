package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void resetRequest(){

    }
}
