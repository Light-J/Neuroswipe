package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;
import com.nsa.cubric.application.services.LoggedUserService;

@Controller
@RequestMapping(value = "/tutorial")
public class Tutorial {
    private static final Logger LOG = LoggerFactory.getLogger(Tutorial.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showTutorialOverview(WebRequest webRequest, Model model){
        System.out.println(LoggedUserService.getUsername());
        LOG.debug("Handling GET request to /tutorial/");
        return "tutorial_overview";
    }
}
