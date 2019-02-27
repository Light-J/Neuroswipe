package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepository;
import com.nsa.cubric.application.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;

@Service
public class RewardServicesStatic implements RewardService {

    private RewardRepository rewardRepository;
    private LoggedUserService loggedUserService;
    private AccountRepository accountRepository;

    @Autowired
    public RewardServicesStatic(RewardRepository rewardRepository, LoggedUserService loggedUserService, AccountRepository accountRepository){
        this.rewardRepository = rewardRepository;
        this.loggedUserService = loggedUserService;
        this.accountRepository = accountRepository;
    }

    @Override
    public Map<String, Integer> getRewardsForUser(){
        Long profile_id = accountRepository.getProfileByEmail(loggedUserService.getUsername()).getId();

        return rewardRepository.getRewardsByProfileId(profile_id);
    }


    @Override
    public boolean updateRewardValue(String reward, int value){

        if(rewardExists(reward)){
            Long profile_id = accountRepository.getProfileByEmail(loggedUserService.getUsername()).getId();
            return rewardRepository.updateRewardValue(profile_id, reward, value);
        } else {
            return false;
        }
    }


    /***
     * This method is used within this class to check to ensure that the reward is a valid reward.
     * This helps with preventing SQL injection as it means random users cannot just make their own post
     * request with some SQL injection
     * @param reward reward value to be verified
     * @return true if safe and false is not
     */
    private boolean rewardExists(String reward){
        String[] potentialRewards = {"training", "practice", "sort25", "sort50", "feedback"};
        return Arrays.asList(potentialRewards).contains(reward);
    }
}

