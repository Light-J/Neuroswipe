package com.nsa.cubric.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login")
public class CustomLoginController {

    @RequestMapping(path = "/disabled", method = RequestMethod.GET)
    public String disabledPage() {
        return "disabled";
    }

    @RequestMapping(path = "/reset", method = RequestMethod.GET)
    public String resetPage(){
        return "reset_request";
    }
}
