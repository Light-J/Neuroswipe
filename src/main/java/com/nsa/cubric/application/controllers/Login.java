package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;


@Controller
public class Login {
    private AccountServiceStatic accountService;

    @Autowired
    public Login(AccountServiceStatic aRepo){
        accountService = aRepo;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String openLoginPage(WebRequest webRequest, Model model){

        model.addAttribute("account", new AccountDTO());

        return "login";
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView submitLoginPage(@ModelAttribute("account") @Valid AccountDTO accountDTO,
                                  BindingResult result, WebRequest webRequest, Errors errors){

//        if(result.hasErrors()){
//            return new ModelAndView("login", "account", accountDTO);
//        }

        Account loginAccount = loginAccount(accountDTO, result);

        if (loginAccount == null){
            System.out.println("ERROR IN LOGIN");
            return new ModelAndView("login", "account", accountDTO);
        }

        return new ModelAndView("success_register", "account", loginAccount);
    }

    private Account loginAccount(AccountDTO accountDTO, BindingResult result){
        Account login;
        try{
            login = accountService.loginUser(accountDTO);
        } catch (Exception e){
            return null;
        }
        return login;
    }
}
