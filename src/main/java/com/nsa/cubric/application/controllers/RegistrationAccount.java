package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.services.AccountServiceStatic;
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

import javax.validation.Valid;


@Controller
@RequestMapping(value = "/registration")
public class RegistrationAccount {
    private AccountServiceStatic accountService;

    @Autowired
    public RegistrationAccount(AccountServiceStatic aRepo){
        accountService = aRepo;
    }

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);

    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /registration/");

        model.addAttribute("account", new AccountDTO());
        return "register_account";
    }

    @RequestMapping(value = "/account", method = RequestMethod.POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("account") @Valid AccountDTO accountDTO,
            BindingResult result,
            WebRequest webRequest,
            Errors errors){

        Account registered = new Account();
        for (ObjectError errorObj :
                errors.getAllErrors()) {
            System.out.println(errorObj.toString());
        }
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
            return new ModelAndView("redirect:/registration/profile", "account", accountDTO);
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
