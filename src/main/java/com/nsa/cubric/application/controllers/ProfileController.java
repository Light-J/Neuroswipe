package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Profile;
import com.nsa.cubric.application.dto.ProfileDto;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.LoggedUserService;
import com.nsa.cubric.application.services.RewardService;
import com.nsa.cubric.application.services.UserRatingService;
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
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/userprofile")
public class ProfileController {

    private AccountService accountService;
    private LoggedUserService loggedUserService;
    private UserRatingService userRatingService;

    @Autowired
    public ProfileController(AccountService accountService, LoggedUserService loggedUserService, UserRatingService userRatingService){
        this.accountService = accountService;
        this.loggedUserService = loggedUserService;
        this.userRatingService = userRatingService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showUserProfile(Model model){
        String userName = loggedUserService.getUsername();
        ProfileDto userProfileDto = new ProfileDto(accountService.getProfileByEmail(userName));

        model.addAttribute("profile", userProfileDto);
        model.addAttribute("ethnicityOptions", accountService.getAllEthnicityOptions());
        model.addAttribute("religionOptions", accountService.getAllReligionOptions());
        model.addAttribute("relationshipOptions", accountService.getAllRelationshipOptions());
        model.addAttribute("sexualOrientationOptions", accountService.getAllSexualOrientationOptions());
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

    @RequestMapping(value = "/certificate", method = RequestMethod.GET)
    public ModelAndView certificateContent(Model model){
        ProfileDto user = new ProfileDto(accountService.getProfileByEmail(loggedUserService.getUsername()));
        model.addAttribute("profile", user);
        model.addAttribute("numberOfRatings", userRatingService.getNumberOfRatingsForUser());

        return new ModelAndView("certificate", "model", model);
    }
}
