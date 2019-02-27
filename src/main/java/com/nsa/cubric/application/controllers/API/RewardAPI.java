package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.controllers.RegistrationController;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Scan;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.AdminServices;
import com.nsa.cubric.application.services.RewardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("api/rewards")
public class RewardAPI {


    private static final Logger LOG = LoggerFactory.getLogger(RegistrationController.class);

    private RewardService rewardService;

    @Autowired
    public RewardAPI(RewardService rewardService){
        this.rewardService = rewardService;

    }


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserRewards() {

        return new ResponseEntity<>(rewardService.getRewardsForUser(), null, HttpStatus.OK);
    }

    @RequestMapping(value = "/set/{reward}", method = RequestMethod.POST)
    public Boolean setUserReward(@RequestParam(value="value") int value,
                                 @PathVariable(value="reward") String reward){
        return rewardService.updateRewardValue(reward, value);
    }



    /*

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
*/
}
