package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepositoryStatic implements FeedbackRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Feedback> feedbackMapper;

    @Autowired
    public FeedbackRepositoryStatic(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        feedbackMapper = (rs, i) -> new Feedback(
                rs.getLong("feedback_id"),
                rs.getLong("profile_id"),
                rs.getString("feedback")
        );
    }

    @Override
    public void insertNewFeedback(Feedback feedback){
        jdbcTemplate.update(
                "INSERT into feedback (profile_id, feedback) values (?,?)",
                feedback.getUserProfileId(), feedback.getFeedback());
    }

    @Override
    public List<Feedback> getAll(){
        return jdbcTemplate.query(
                "SELECT feedback_id, profile_id, feedback FROM feedback",
                new Object[]{},feedbackMapper
        );
    }
}
