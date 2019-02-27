package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Scan;

import java.util.List;
import java.util.Map;
import java.util.Optional;

interface RewardRepository {
    public Map<String, Integer> getRewardsByProfileId(Long profile_id);

}
