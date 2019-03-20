package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;

@Controller
public class IntroductionController {

    private static final Logger LOG = LoggerFactory.getLogger(IntroductionController.class);

    //Handles get request for homepage
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String homepage() {
        LOG.debug("Handling GET request to /");
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error") String error){

        return new ModelAndView("redirect:/#loginError");
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
