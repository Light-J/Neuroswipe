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
    public ChangeDetails(AccountRepository aRepo){
        accountRepository = aRepo;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showAdminPage(Model model){

        String userName = LoggedUserService.getUsername();

        System.out.println("userName:");
        System.out.println(userName);

        ProfileDTO userProfile = accountRepository.getProfileByEmail(userName);

        System.out.println("userProfile:");
        System.out.println(userProfile);
        System.out.println("userProfile.getAge():");
        System.out.println(userProfile.getAge());

        model.addAttribute("profile", userProfile);

        return "changedetails";
    }

}
