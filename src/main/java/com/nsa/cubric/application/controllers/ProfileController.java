package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/userprofile")
public class ProfileController {

    private AccountService accountService;
    private LoggedUserService loggedUserService;

    @Autowired
    public ProfileController(AccountService accountService, LoggedUserService loggedUserService){
        this.accountService = accountService;
        this.loggedUserService = loggedUserService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showUserProfile(Model model){
        String userName = loggedUserService.getUsername();
        ProfileDto userProfileDto = accountService.getProfileByEmail(userName);
        model.addAttribute("profile", userProfileDto);
        return new ModelAndView("user_profile", "model", model);
    }

    @RequestMapping(value="/", method = RequestMethod.POST)
    public ModelAndView updateUserProfile(@ModelAttribute("profile") @Valid ProfileDto profileDto, BindingResult result){

        if (result.hasErrors()){
            return new ModelAndView("user_profile", "profile", profileDto);
        }

        Boolean updateProfile = accountService.updateProfile(profileDto);
        return new ModelAndView("user_profile", "profile", profileDto);
    }
}
