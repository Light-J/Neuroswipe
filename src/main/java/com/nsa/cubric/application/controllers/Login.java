package com.nsa.cubric.application.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class Login {

    @GetMapping(value = "/")
    public String getLoginPage() {
        return "login";
    }
}
