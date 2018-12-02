package com.nsa.cubric.application.controllers.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminAPI {

    @PostMapping(value = "/removeUser")
    public String removeUser(@RequestParam Integer userId) {

        return "success";
    }
}
