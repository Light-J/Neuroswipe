package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.accountUtils.EmailExistsException;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/registration")
public class RegistrationForm {
    private AccountServiceStatic accountService;

    @Autowired
    public RegistrationForm(AccountServiceStatic aRepo){
        accountService = aRepo;
    }

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationForm.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /registration/");

        model.addAttribute("account", new AccountDTO());
        return "register_account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("account") @Valid AccountDTO accountDTO,
            BindingResult result, WebRequest webRequest, Errors errors){

        Account registered = new Account();

        if(!result.hasErrors()){
            LOG.debug("Creating user account");
            registered = createUserAccount(accountDTO, result);
        }
//        if(registered == null){
//            result.rejectValue("email", "message.regError");
//        }

        if(result.hasErrors() || registered == null){
            return new ModelAndView("register_account", "account", accountDTO);
        } else {
            return new ModelAndView("success_register", "account", accountDTO);
        }



    }

    private Account createUserAccount(AccountDTO accountDTO, BindingResult result){
        Account registered = null;
        try{
            registered = accountService.registerNewUser(accountDTO);
        } catch (EmailExistsException e){
            return null;
        }
        return registered;
    }
}
