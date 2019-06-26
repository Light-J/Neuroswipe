package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.dto.PaginatedList;

import java.util.List;

public interface FeedbackRepository {
    List<Feedback> getAll();
    void insertNewFeedback(FeedbackForm feedbackForm);
    PaginatedList getFeedbackComments(int offset, int pageSize);
}
