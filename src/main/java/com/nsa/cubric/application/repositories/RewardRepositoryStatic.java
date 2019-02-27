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
            results.put("training", rs.getInt("training"));
            results.put("practice", rs.getInt("practice"));
            results.put("sort25", rs.getInt("sort25"));
            results.put("sort50", rs.getInt("sort50"));
            results.put("feedback", rs.getInt("feedback"));
            return results;
        });
    }


    @Override
    public boolean updateRewardValue(Long profileId, String reward, int value){
        String sql = "UPDATE reward SET "+reward+"=? WHERE profile_id = ?";
        return jdbcTemplate.update(sql, value, profileId) == 1;
    }


    @Override
    public boolean checkIfUserHasReward(Long profileId, String reward){
        String sql = "SELECT " + reward +" FROM reward WHERE profile_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, profileId ) == 1;
    }

}
