package com.nsa.cubric.application.services;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;
import com.nsa.cubric.application.repositories.AccountRepositoryStatic;
import com.nsa.cubric.application.repositories.FeedbackRepositoryStatic;
import com.nsa.cubric.application.services.registrationUtils.EmailExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.nsa.cubric.application.configurators.WebSecurityConfig.passwordEncoder;

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
