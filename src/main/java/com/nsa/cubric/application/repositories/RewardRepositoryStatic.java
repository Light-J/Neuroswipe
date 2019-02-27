package com.nsa.cubric.application.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

@Repository
public class RewardRepositoryStatic implements RewardRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public RewardRepositoryStatic(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

    }

    @Override
    public Map<String, Integer> getRewardsByProfileId(Long profileId){

        return jdbcTemplate.query("SELECT * FROM reward WHERE profile_id = ?",new Object[] {profileId}, (ResultSet rs) -> {
            HashMap<String,Integer> results = new HashMap<>();
            rs.next();
            results.put("training", rs.getInt("reward_training"));
            results.put("practice", rs.getInt("reward_practice"));
            results.put("sort25", rs.getInt("reward_sort_25"));
            results.put("sort50", rs.getInt("reward_sort_50"));
            results.put("feedback", rs.getInt("reward_feedback"));
            return results;
        });
    }


    @Override
    public boolean updateRewardValue(Long profileId, String reward, int value){
        return jdbcTemplate.update("UPDATE reward SET ?=? WHERE profile_id = ?", reward, value, profileId) == 1;
    }

}
