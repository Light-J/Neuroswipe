package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.configurators.MyUserPrincipal;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.MyUserDetailsService;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
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
        String originalPassword = accountDTO.getPassword();

        if(!result.hasErrors()){
            LOG.debug("Creating user account");
            registered = createUserAccount(accountDTO, result);
        }

        if(registered == null){
            result.addError(new ObjectError("email", "Email already exists"));
        }

        if(result.hasErrors()){
            return new ModelAndView("register_account", "account", accountDTO);
        } else {

            return new ModelAndView("redirect:/login", "account", accountDTO);
        }
    }

    private Account createUserAccount(AccountDTO accountDTO, BindingResult result){
        Account registered = null;
        try{
            registered = accountService.registerNewUserAccount(accountDTO);
        } catch (EmailExistsException e){
            return null;
        }
        return registered;
    }
}
