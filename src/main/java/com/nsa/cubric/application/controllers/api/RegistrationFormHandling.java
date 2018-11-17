package com.nsa.cubric.application.controllers.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/registration")
public class RegistrationFormHandling {

    @RequestMapping(value = "/test")
    public String returnTest(){
        return "This is my test";
    }
}
