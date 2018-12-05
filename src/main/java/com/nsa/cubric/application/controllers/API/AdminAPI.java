package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.AdminServicesStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/admin/utilities")
public class AdminAPI {

    @Autowired
    AdminServicesStatic adminServices;

    @Autowired
    AccountServiceStatic accountService;

    @PostMapping(value = "/removeUser")
    public Boolean removeUser(@RequestParam(value = "user_to_remove") String userEmail) {
        String userId = accountService.findByEmail(userEmail).getId().toString();
        return adminServices.removeUser(Integer.parseInt(userId));
    }

    @PostMapping(value = "/removeUserResponses")
    public Integer removeUserResponses(@RequestParam(value = "user_to_remove_responses") String userId){
        return adminServices.removeUserResponses(Integer.parseInt(userId));
    }
}
