package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/registration")
public class RegistrationDetails {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String showRegistrationForm(Model model){
        LOG.debug("Handling GET request to /registration/");

        model.addAttribute("account", new AccountDTO());
        return "register_account";
    }


}
