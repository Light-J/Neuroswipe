package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.services.AccountServiceStatic;
import com.nsa.cubric.application.services.FeedbackServiceStatic;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/feedback")
public class FeedbackAPI {

    private FeedbackServiceStatic feedbackService;

    @Autowired
    public FeedbackAPI(FeedbackServiceStatic aRepo) {
        feedbackService = aRepo;
    }

    @Autowired
    LoggedUserService loggedUserService;

    @Autowired
    AccountServiceStatic accountService;


    /**
     * This method is accepts posted JSON containing feedback information. It responds to POST
     * requests to /feedback.
     * @return Boolean true indicating success.
     */
    @PostMapping(value = "/")
    public Boolean addFeedback(@RequestParam(value = "feedback") String feedbackText) {
        Account loggedInUser = accountService.findByEmail(loggedUserService.getUsername());
        Feedback feedback = new Feedback(null, loggedInUser.getId(), feedbackText);
        feedbackService.insertNewFeedback(feedback);
        return true;
    }

    /**
     * This method is used to return the JSON for all the feedback entries. It responds to GET
     * requests to /feedback.
     * @return ResponseEntity object containing JSON.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getFeedback() {
        List<Feedback> feedback = feedbackService.getAll();
        return new ResponseEntity<>(feedback, null, HttpStatus.OK);
    }
}
