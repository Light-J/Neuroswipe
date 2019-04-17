package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;

import java.util.List;

public interface FeedbackService {
    public List<Feedback> getAll();
    public void insertNewFeedback(FeedbackForm feedbackForm);
}
