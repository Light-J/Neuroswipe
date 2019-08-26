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
            results.put("sort20", rs.getInt("sort20"));
            results.put("sort40", rs.getInt("sort40"));
            results.put("sort60", rs.getInt("sort60"));
            results.put("sort80", rs.getInt("sort80"));
            results.put("sort100", rs.getInt("sort100"));
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
