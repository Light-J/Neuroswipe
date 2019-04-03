package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepository;
import com.nsa.cubric.application.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class RewardServicesStatic implements RewardService {

    private RewardRepository rewardRepository;
    private LoggedUserService loggedUserService;

    @Autowired
    public RewardServicesStatic(RewardRepository rewardRepository, LoggedUserService loggedUserService){
        this.rewardRepository = rewardRepository;
        this.loggedUserService = loggedUserService;
    }

    @Override
    public Map<String, Integer> getRewardsForUser(){

        return rewardRepository.getRewardsByProfileId(loggedUserService.getUserProfileId());
    }


    @Override
    public boolean updateRewardValue(String reward, int value){
        if(rewardExists(reward)){
            return rewardRepository.updateRewardValue(loggedUserService.getUserProfileId(), reward, value);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkIfUserHasReward(String reward){
        long profileId = loggedUserService.getUserProfileId();
        if(profileId == -1){
            return true;
        }

        if(rewardExists(reward)){
            return rewardRepository.checkIfUserHasReward(profileId, reward);
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

