package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountServiceStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationDetails {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);

    @Autowired
    private AccountServiceStatic accountService;

    @GetMapping(value = "/profile")
    public String showRegistrationForm(Model model){
        LOG.debug("Handling GET request to /registration/profile");

        model.addAttribute("profile", new ProfileDTO());
        return "register_details";
    }

    @PostMapping(value = "/profile")
    public ModelAndView registerUserDetails(@ModelAttribute("profile") @Valid ProfileDTO profileDTO,
                                            Errors errors,
                                            BindingResult result,
                                            WebRequest webRequest
                                            ) {

        LOG.debug("Handling POST to /registration/profile");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Account loggedInUser = accountService.findByEmail(auth.getName());
        profileDTO.setLoggedInUserId(loggedInUser.getId());
        accountService.registerNewUserProfile(profileDTO);



        if (result.hasErrors()){
            return new ModelAndView("register_details", "profile", profileDTO);
        }
        return new ModelAndView("redirect:/home");
    }




}
