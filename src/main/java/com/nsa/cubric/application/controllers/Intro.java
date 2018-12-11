package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Intro {

    private static final Logger LOG = LoggerFactory.getLogger(Intro.class);

    //Handles get request for homepage
    @RequestMapping(path = "/")
    public String homepage() {
        LOG.debug("Handling GET request to /");
        return "home";
    }

    @RequestMapping(path = "/about")
    public String about() {
        LOG.debug("Handling GET request to /about");
        return "about_brain_imaging";
    }

    @RequestMapping(path = "/training")
    public String training() {
        LOG.debug("Handling GET request to /training");
        return "training";
    }
}
