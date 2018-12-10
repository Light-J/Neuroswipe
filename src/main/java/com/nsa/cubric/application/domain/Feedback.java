package com.nsa.cubric.application.domain;

public class Feedback {
    Long id;
    Long userProfileId;
    String feedback;

    public Feedback(Long id, Long userProfileId, String feedback) {
        this.id = id;
        this.userProfileId = userProfileId;
        this.feedback = feedback;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserProfileId() {
        return this.userProfileId;
    }

    public void setUserProfileId(Long userProfileId) {
        this.userProfileId = userProfileId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
