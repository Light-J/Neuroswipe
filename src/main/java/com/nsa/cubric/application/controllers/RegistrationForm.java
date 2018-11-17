package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/registration")
public class RegistrationForm {

    private static final Logger LOG = LoggerFactory.getLogger(RegistrationForm.class);

    @RequestMapping(value = "/")
    public String register(){
        LOG.debug("Handling GET request to /registration/");
        return "register";
    }
}
