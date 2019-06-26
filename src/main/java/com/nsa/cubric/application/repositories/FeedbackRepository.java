package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;

import java.util.List;

public interface FeedbackRepository {
    List<Feedback> getAll();
    void insertNewFeedback(FeedbackForm feedbackForm);
    List<String> getFeedbackComments();
}
