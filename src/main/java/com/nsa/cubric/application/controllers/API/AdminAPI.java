package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.services.AdminServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("admin/features")
public class AdminAPI {

    @Autowired
    AdminServicesStatic adminServices;

    @PostMapping(value = "/removeUser")
    public Boolean removeUser(@RequestParam Integer userId) {
        return adminServices.removeUser(userId);
    }

    @PostMapping(value = "/removeUserResponses")
    public Integer removeUserResponses(@RequestParam Integer userId){
        return adminServices.removeUserResponses(userId);
    }
}
