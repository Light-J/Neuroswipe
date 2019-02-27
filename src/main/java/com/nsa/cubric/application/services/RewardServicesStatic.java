package com.nsa.cubric.application.services;

import com.nsa.cubric.application.repositories.AccountRepository;
import com.nsa.cubric.application.repositories.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
