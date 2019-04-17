package com.nsa.cubric.application.controllers;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/userfeedback")
public class FeedbackController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showFeedbackForm(Model model){

        FeedbackForm feedbackForm = new FeedbackForm();
        model.addAttribute("FeedbackForm", feedbackForm);

        return "feedback_form";
    }

    @RequestMapping(value = "/submitted", method = RequestMethod.GET)
    public String showFeedbackSubmittedPage(){
        return "feedback_submitted";
    }

    @PostMapping(value = "/submitfeedback")
    public String submitForm(@ModelAttribute("feedback") @Valid FeedbackForm feedForm, BindingResult bindingResult, Model model){


        System.out.println(feedForm.getAccessibility());
        System.out.println(feedForm.getInformation1());
        System.out.println(feedForm.getSorting());

        return "feedback_submitted";
    }
}
