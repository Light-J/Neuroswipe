package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.repositories.FeedbackRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/userfeedback")
public class FeedbackController {

    private final FeedbackRepositoryStatic feedbackRepositoryStatic;

    @Autowired
    public FeedbackController(FeedbackRepositoryStatic feedbackRepositoryStatic) {
        this.feedbackRepositoryStatic = feedbackRepositoryStatic;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showFeedbackForm(Model model){

        model.addAttribute("FeedbackForm", new FeedbackForm());

        return "feedback_form";
    }

    @RequestMapping(value = "/submitted", method = RequestMethod.GET)
    public String showFeedbackSubmittedPage(){
        return "feedback_submitted";
    }

    @PostMapping(value = "/submitfeedback")
    public String submitForm(@ModelAttribute("feedback") @Valid FeedbackForm feedForm, BindingResult bindingResult, Model model){


        Account account = new Account();


        System.out.println("ACCOUNT ID: " + account.getId());

        System.out.println(feedForm.getAccessibility());
        System.out.println(feedForm.getInformation1());
        System.out.println(feedForm.getSorting());

        feedbackRepositoryStatic.insertNewFeedback(feedForm);

        return "feedback_submitted";
    }
}
