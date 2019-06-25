package com.nsa.cubric.application.domain;

import com.nsa.cubric.application.domain.feedbackItems.*;

public class FeedbackOverview {
    Info1 info1;
    Info2 info2;
    Reward reward;
    Sorting sorting;
    Training training;
    EaseOfAccess easeOfAccess;

    public FeedbackOverview() {
        info1 = new Info1();
        info2 = new Info2();
        reward = new Reward();
        sorting = new Sorting();
        training = new Training();
        easeOfAccess = new EaseOfAccess();
    }

    public void addFigures(Feedback feedback){
        info1.addFigures(feedback.getInfo1());
        info2.addFigures(feedback.getInfo2());
        training.addFigures(feedback.getTraining());
        sorting.addFigures(feedback.getSorting());
        reward.addFigures(feedback.getReward());
        easeOfAccess.addFigures(feedback.getEaseOfUse());


    }

    public Info1 getInfo1() {
        return info1;
    }

    public Info2 getInfo2() {
        return info2;
    }

    public Reward getReward() {
        return reward;
    }

    public Sorting getSorting() {
        return sorting;
    }

    public Training getTraining() {
        return training;
    }

    public EaseOfAccess getEaseOfAccess() {
        return easeOfAccess;
    }
}
