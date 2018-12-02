package com.nsa.cubric.application.controllers.API;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin")
public class AdminAPI {

    @PostMapping(value = "/removeUser")
    public String removeUser(@RequestParam Integer userId) {

        return "success";
    }

    @PostMapping(value = "/removeUserResponses")
    public String removeUserResponses(@RequestParam Integer userId){

        return "success";
    }
}
