package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.dto.AccountDto;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.services.AccountService;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.apache.tomcat.jni.Error;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/registration")
public class RegistrationController {
    private AccountService accountService;

    @Autowired
    public RegistrationController(AccountService aRepo){
        accountService = aRepo;
    }
    
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /registration/account");

        model.addAttribute("account", new AccountDto());
        return "register_account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("account") @Valid AccountDto accountDto,
            BindingResult result,
            HttpServletRequest request){

        Account registered = new Account();
        String originalPassword = accountDto.getPassword();

        result = accountService.checkPasswordStrength(accountDto, result);

        if(!result.hasErrors()){
            LOG.debug("Creating user account");
            registered = createUserAccount(accountDto);
        }

        if(registered == null){
            result.addError(new ObjectError("email", "Email already exists"));
        }

        if(result.hasErrors()){
            return new ModelAndView("register_account", "account", accountDto);
        } else {
            authWithHttpServletRequest(request, accountDto.getEmail(), originalPassword);
            return new ModelAndView("redirect:/", "account", accountDto);
        }
    }

    private Account createUserAccount(AccountDto accountDto){
        Account registered = null;
        try{
            registered = accountService.registerNewUserAccount(accountDto);
        } catch (EmailExistsException e){
            return null;
        }
        return registered;
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
        } catch (ServletException e) {
            LOG.debug("Error logging in");
        }
    }
}
