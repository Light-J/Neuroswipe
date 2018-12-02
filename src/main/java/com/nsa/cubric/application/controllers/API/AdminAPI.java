package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.services.AdminServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/admin/utilities")
public class AdminAPI {

    @Autowired
    AdminServicesStatic adminServices;

    @PostMapping(value = "/removeUser")
    public Boolean removeUser(@RequestHeader(value = "user_to_remove") String userId) {
        return adminServices.removeUser(Integer.parseInt(userId));
    }

    @PostMapping(value = "/removeUserResponses")
    public Integer removeUserResponses(@RequestParam Integer userId){
        return adminServices.removeUserResponses(userId);
    }
}
