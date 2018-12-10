package com.nsa.cubric.application.repositories;

import com.nsa.cubric.application.controllers.AccountDTO;
import com.nsa.cubric.application.controllers.ProfileDTO;
import com.nsa.cubric.application.domain.Account;
import com.nsa.cubric.application.domain.Feedback;

import java.util.List;

public interface FeedbackRepositoryStatic {
    public List<Feedback> getAll();
    public void insertNewFeedback(Feedback feedback);
}
