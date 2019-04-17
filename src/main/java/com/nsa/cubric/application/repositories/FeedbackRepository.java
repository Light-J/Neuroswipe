package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;

import java.util.List;

public interface FeedbackRepository {
    public List<Feedback> getAll();
    public void insertNewFeedback(FeedbackForm feedbackForm);
}
