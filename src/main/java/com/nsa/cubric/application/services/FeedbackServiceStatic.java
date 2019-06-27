package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.domain.FeedbackForm;
import com.nsa.cubric.application.domain.FeedbackOverview;
import com.nsa.cubric.application.dto.PaginatedList;
import com.nsa.cubric.application.repositories.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceStatic implements FeedbackService {

    private FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceStatic(FeedbackRepository aRepo){
        feedbackRepository = aRepo;
    }

    @Override
    public void insertNewFeedback(FeedbackForm feedbackForm){
        feedbackRepository.insertNewFeedback(feedbackForm);
    }

    @Override
    public FeedbackOverview getFeedbackOverview() {
        List<Feedback> allFeedback = feedbackRepository.getAll();
        FeedbackOverview overview = new FeedbackOverview();

        for (Feedback feedback : allFeedback) {
            overview.addFigures(feedback);
        }


        return overview;
    }

    @Override
    public PaginatedList getFeedbackComments(int page, int pageSize){
        return feedbackRepository.getFeedbackComments(pageSize*(page-1), pageSize);
    }

}
