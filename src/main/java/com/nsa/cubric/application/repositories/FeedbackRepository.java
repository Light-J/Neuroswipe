package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;

import java.util.List;

public interface FeedbackRepository {
    public List<Feedback> getAll();
    public void insertNewFeedback(Feedback feedback);
}
