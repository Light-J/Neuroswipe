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

    @RequestMapping(value = "/get/{reward}", method = RequestMethod.GET)
    public Boolean checkIfUserHasReward(@PathVariable(value = "reward") String reward){
        return rewardService.checkIfUserHasReward(reward);
    }

}
