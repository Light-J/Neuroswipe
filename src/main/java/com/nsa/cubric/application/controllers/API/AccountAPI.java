package com.nsa.cubric.application.controllers.API;


import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.registrationUtils.EmailValidator;
import com.nsa.cubric.application.services.registrationUtils.ValidEmail;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
