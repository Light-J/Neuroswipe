package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
@RequestMapping(value = "/registration")
public class RegistrationDetails {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);


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
                                            WebRequest webRequest) {

        LOG.debug("Handling POST to /registration/profile");

        for (FieldError fieldError :
                errors.getFieldErrors()) {
            System.out.println("Field of error: " + fieldError.getField() +
                    "\nError message: " + fieldError.getDefaultMessage());
        }

        if (result.hasErrors()){
            return new ModelAndView("register_details", "profile", profileDTO);
        }
        return new ModelAndView("success_register");
    }


}
