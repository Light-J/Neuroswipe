package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.repositories.FeedbackRepositoryStatic;
import com.nsa.cubric.application.services.FeedbackServiceStatic;
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

    private final FeedbackServiceStatic feedbackServiceStatic;

    @Autowired
    public FeedbackController(FeedbackServiceStatic feedbackServiceStatic) {
        this.feedbackServiceStatic = feedbackServiceStatic;
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

        feedbackServiceStatic.insertNewFeedback(feedForm);

        return "feedback_submitted";
    }
}
