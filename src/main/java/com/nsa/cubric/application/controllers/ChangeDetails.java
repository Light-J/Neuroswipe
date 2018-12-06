package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.repositories.AccountRepository;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/changedetails")
public class ChangeDetails {

    AccountRepository accountRepository;

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    public ChangeDetails(AccountRepository aRepo){
        accountRepository = aRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage(Model model){

        String userName = loggedUserService.getUsername();

        ProfileDTO userProfile = accountRepository.getProfileByEmail(userName);

        model.addAttribute("profile", userProfile);

        return "changedetails";
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String showAdminPage(Model model, ProfileDTO profile){

        Boolean updateProfile = accountRepository.updateProfile(profile);

        model.addAttribute("profile", profile);
        return "changedetails";
    }

}
