package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.controllers.RegistrationAccount;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.AdminServicesStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/admin")
public class AdminAPI {


    private static final Logger LOG = LoggerFactory.getLogger(RegistrationAccount.class);

    @Autowired
    AdminServicesStatic adminServices;

    @Autowired
    AccountServiceStatic accountService;

    @PostMapping(value = "/removeUser")
    public Boolean removeUser(@RequestParam(value = "userToRemove") String userEmail) {
        Long userId;
        try{
            userId = accountService.getAccountByEmail(userEmail).getId();
        } catch (NullPointerException e){
            return false;
        }
        return adminServices.removeUser(userId);
    }

    @PostMapping(value = "/removeUserResponses")
    public Integer removeUserResponses(
            @RequestParam(value = "userToRemoveResponses") String userEmail){
        try{
            return adminServices.removeUserResponses(accountService.getAccountByEmail(userEmail).getId());
        } catch (NullPointerException e){
            return 0;
        }
    }

    @GetMapping(value = "/searchUsers")
    public List<Account> searchUsers(
            @RequestParam(value = "searchTerm") String searchTerm,
            @RequestParam(value = "page") int page) {
        return accountService.searchUsers(searchTerm, page);
    }

    @PostMapping(value = "/{userId}/role")
    public boolean updateUserRole(
            @PathVariable(value = "userId") Long userId,
            @RequestBody() String role){

        return adminServices.updateUserRole(userId, role.replace("role=", ""));
    }

}
