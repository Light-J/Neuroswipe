package com.nsa.cubric.application.services;

import java.util.Map;

public interface RewardService {
   Map<String, Integer> getRewardsForUser();
   boolean updateRewardValue(String reward, int value);
   boolean checkIfUserHasReward(String reward);
}
