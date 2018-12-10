package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepository implements FeedbackRepositoryStatic {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Feedback> feedbackMapper;

    @Autowired
    public FeedbackRepository(JdbcTemplate aTemplate) {
        jdbcTemplate = aTemplate;

        feedbackMapper = (rs, i) -> new Feedback(
                rs.getLong("id"),
                rs.getLong("userprofileid"),
                rs.getString("feedback")
        );
    }

    @Override
    public void insertNewFeedback(Feedback feedback){
        jdbcTemplate.update(
                "INSERT into userfeedback (userprofileid, feedback) values (?,?)",
                feedback.getUserProfileId(), feedback.getFeedback());
    }

    @Override
    public List<Feedback> getAll(){
        return jdbcTemplate.query(
                "SELECT id, userprofileid, feedback FROM userfeedback",
                new Object[]{},feedbackMapper
        );
    }
}
