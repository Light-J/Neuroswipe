package com.nsa.cubric.application.services;

import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.repositories.FeedbackRepositoryStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService implements FeedbackServiceStatic {

    private FeedbackRepositoryStatic feedbackRepository;

    @Autowired
    public FeedbackService(FeedbackRepositoryStatic aRepo){
        feedbackRepository = aRepo;
    }

    @Override
    public void insertNewFeedback(Feedback feedback){
        feedbackRepository.insertNewFeedback(feedback);
    }

    @Override
    public List<Feedback> getAll(){
        return feedbackRepository.getAll();
    }
}
