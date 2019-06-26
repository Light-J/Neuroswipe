package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.domain.FeedbackOverview;
import com.nsa.cubric.application.dto.PaginatedList;

import java.util.List;

public interface FeedbackService {
    FeedbackOverview getFeedbackOverview();
    void insertNewFeedback(FeedbackForm feedbackForm);
    PaginatedList getFeedbackComments(int page, int pageSize);

}
