package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//controller
@Controller
public class Intro {

    private static final Logger LOG = LoggerFactory.getLogger(Intro.class);

    //Handles get request for homepage
    @RequestMapping(path = "/")
    public String homepage() {
        LOG.debug("Handling GET request to /");
        return "home";
    }
}
