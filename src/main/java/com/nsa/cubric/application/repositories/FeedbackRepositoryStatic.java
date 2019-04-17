package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.FeedbackService;
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
    private AccountService accountService;
    private FeedbackService feedbackService;

    public FeedbackRepositoryStatic(LoggedUserService loggedUserService, AccountService accountService, FeedbackService feedbackService) {
        this.loggedUserService = loggedUserService;
        this.accountService = accountService;
        this.feedbackService = feedbackService;
    }

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
    public void insertNewFeedback(FeedbackForm feedbackForm){

        Long userID = loggedUserService.getUserProfileId();

//        loggedUserService.
//
//        String other = loggedUserService.getUsername();
//
//        System.out.println(other);

        jdbcTemplate.update(
                "INSERT into feedback(profile_id, info_1, info_2, training, sorting, reward, ease_of_use, access) values (?,?,?,?,?,?,?,?)",
                userID, feedbackForm.getInformation1(), feedbackForm.getInformation2(), feedbackForm.getTraining(), feedbackForm.getSorting(), feedbackForm.getReward(), feedbackForm.getEaseOfUse(), feedbackForm.getAccessibility());
    }

    @Override
    public List<Feedback> getAll(){
        return jdbcTemplate.query(
                "SELECT feedback_id, profile_id, feedback FROM feedback",
                new Object[]{},feedbackMapper
        );
    }
}
