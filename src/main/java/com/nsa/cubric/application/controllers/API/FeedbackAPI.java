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

@RequestMapping("/feedback")
@RestController
public class FeedbackAPI {
    private LoggedUserService loggedUserService;
    private AccountServiceStatic accountService;
    private FeedbackServiceStatic feedbackService;

    @Autowired
    public FeedbackAPI(LoggedUserService loggedUserService, AccountServiceStatic accountService, FeedbackServiceStatic feedbackService) {
        this.loggedUserService = loggedUserService;
        this.accountService = accountService;
        this.feedbackService = feedbackService;
    }

    /**
     * This method is accepts posted JSON containing feedback information. It responds to POST
     * requests to /feedback.
     * @return Boolean true indicating success.
     */
    @PostMapping(value = "/", produces = "application/json")
    public ResponseEntity addFeedback(@RequestParam(value = "feedbackText") String feedbackText) {
        if(loggedUserService.getUsername() == null){
            return new ResponseEntity<>(false, null, HttpStatus.FORBIDDEN);
        }
        Account loggedInUser = accountService.getAccountByEmail(loggedUserService.getUsername());
        Feedback feedback = new Feedback(null, loggedInUser.getId(), feedbackText);
        feedbackService.insertNewFeedback(feedback);
        return new ResponseEntity<>(true, null, HttpStatus.OK);
    }

    /**
     * This method is used to return the JSON for all the feedback entries. It responds to GET
     * requests to /feedback.
     * @return ResponseEntity object containing JSON.
     */
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getFeedback() {
        List<Feedback> feedback = feedbackService.getAll();

        for (Feedback feedbackObject : feedback) {
            Account account = accountService.getAccountById(feedbackObject.getUserProfileId());
            feedbackObject.setUserEmail(account.getEmail());
        }

        return new ResponseEntity<>(feedback, null, HttpStatus.OK);
    }
}
