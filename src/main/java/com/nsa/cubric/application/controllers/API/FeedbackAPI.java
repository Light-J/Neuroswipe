package com.nsa.cubric.application.controllers.API;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackOverview;
import com.nsa.cubric.application.dto.PaginatedList;
import com.nsa.cubric.application.services.AccountService;
import com.nsa.cubric.application.services.FeedbackService;
import com.nsa.cubric.application.services.LoggedUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/feedback")
@RestController
public class FeedbackAPI {
    private LoggedUserService loggedUserService;
    private AccountService accountService;
    private FeedbackService feedbackService;

    @Autowired
    public FeedbackAPI(LoggedUserService loggedUserService, AccountService accountService, FeedbackService feedbackService) {
        this.loggedUserService = loggedUserService;
        this.accountService = accountService;
        this.feedbackService = feedbackService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getFeedbackData(){
        FeedbackOverview overview = feedbackService.getFeedbackOverview();
        return new ResponseEntity<>(overview, null, HttpStatus.OK);
    }

    @RequestMapping(value = "/comment", method = RequestMethod.GET)
    public ResponseEntity getFeedbackComments(@RequestParam(value = "page") int page, @RequestParam(value = "page-size") int pageSize){
        PaginatedList data = feedbackService.getFeedbackComments(page, pageSize);
        return new ResponseEntity(data, HttpStatus.OK);
    }
}
