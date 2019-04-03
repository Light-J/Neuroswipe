package com.nsa.cubric.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class FailedLoginController {

    @RequestMapping(path = "/disabled", method = RequestMethod.GET)
    public String homepage() {
        return "disabled";
    }

}
