package com.nsa.cubric.application.repositories;

import java.util.Map;

public interface RewardRepository {
    public Map<String, Integer> getRewardsByProfileId(Long profileId);
    public boolean updateRewardValue(Long profileId, String reward, int value);
    public boolean checkIfUserHasReward(Long profileId, String reward);
}
