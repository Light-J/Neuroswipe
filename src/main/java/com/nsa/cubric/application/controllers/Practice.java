package com.nsa.cubric.application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

@Controller
@RequestMapping(value = "/practice")
public class Practice {
    private static final Logger LOG = LoggerFactory.getLogger(Practice.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showTutorialOverview(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /practice/");
        return "practice";
    }

    @RequestMapping(value = "/feedback", method = RequestMethod.GET)
    public String showPracticeFeedback(WebRequest webRequest, Model model){
        LOG.debug("Handling GET request to /feedback/");
        return "practice_feedback";
    }
}
