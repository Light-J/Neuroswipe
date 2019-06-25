package com.nsa.cubric.application.domain;

public class Feedback {
    String info1;
    String info2;
    String training;
    String sorting;
    String reward;
    String easeOfUse;
    String access;

    public Feedback(String info1, String info2, String training, String sorting, String reward, String easeOfUse, String access) {
        this.info1 = info1;
        this.info2 = info2;
        this.training = training;
        this.sorting = sorting;
        this.reward = reward;
        this.easeOfUse = easeOfUse;
        this.access = access;
    }

    public String getInfo1() {
        return info1;
    }

    public String getInfo2() {
        return info2;
    }

    public String getTraining() {
        return training;
    }

    public String getSorting() {
        return sorting;
    }

    public String getReward() {
        return reward;
    }

    public String getEaseOfUse() {
        return easeOfUse;
    }

    public String getAccess() {
        return access;
    }
}
