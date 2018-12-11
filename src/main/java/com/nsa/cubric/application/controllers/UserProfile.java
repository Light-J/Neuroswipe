package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping(value = "/userprofile")
public class UserProfile {

    AccountServiceStatic accountService;
    LoggedUserService loggedUserService;

    @Autowired
    public UserProfile(AccountServiceStatic accountService, LoggedUserService loggedUserService){
        this.accountService = accountService;
        this.loggedUserService = loggedUserService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showUserProfile(Model model){
        String userName = loggedUserService.getUsername();
        Profile userProfile = accountService.getProfileByEmail(userName);
        model.addAttribute("profile", userProfile);
        return new ModelAndView(
                "user_profile",
                (Map<String, ?>) model.asMap());
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public String updateUserProfile(Model model, Profile profile){
        Boolean updateProfile = accountService.updateProfile(profile);
        model.addAttribute("profile", profile);
        return "user_profile";
    }

}
