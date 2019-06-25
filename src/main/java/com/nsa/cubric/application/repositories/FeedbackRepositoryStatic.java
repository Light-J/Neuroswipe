package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FeedbackRepositoryStatic implements FeedbackRepository {
    private JdbcTemplate jdbcTemplate;
    private RowMapper<Feedback> feedbackMapper;
    private LoggedUserService loggedUserService;


    @Autowired
    public FeedbackRepositoryStatic(JdbcTemplate aTemplate, LoggedUserService loggedUserService) {
        jdbcTemplate = aTemplate;
        this.loggedUserService = loggedUserService;

        feedbackMapper = (rs, i) -> new Feedback(
                rs.getString("info_1"),
                rs.getString("info_2"),
                rs.getString("training"),
                rs.getString("sorting"),
                rs.getString("reward"),
                rs.getString("ease_of_use"),
                rs.getString("access")
        );

    }

    @Override
    public void insertNewFeedback(FeedbackForm feedbackForm){

        Long userID = loggedUserService.getUserProfileId();


        jdbcTemplate.update(
                "INSERT into feedback(profile_id, info_1, info_2, training, sorting, reward, ease_of_use, access) values (?,?,?,?,?,?,?,?)",
                userID, feedbackForm.getInformation1(), feedbackForm.getInformation2(), feedbackForm.getTraining(), feedbackForm.getSorting(), feedbackForm.getReward(), feedbackForm.getEaseOfUse(), feedbackForm.getAccessibility());
    }

    @Override
    public List<Feedback> getAll(){
        return jdbcTemplate.query(
                "SELECT * FROM feedback",
                new Object[]{},feedbackMapper
        );
    }
}
