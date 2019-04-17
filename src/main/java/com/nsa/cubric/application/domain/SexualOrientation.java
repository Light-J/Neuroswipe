package com.nsa.cubric.application.domain;

public class SexualOrientation {
    Integer sexualOrientationId;
    String sexualOrientation;

    public SexualOrientation(Integer sexualOrientationId, String sexualOrientation) {
        this.sexualOrientationId = sexualOrientationId;
        this.sexualOrientation = sexualOrientation;
    }

    public Integer getSexualOrientationId() {
        return sexualOrientationId;
    }

    public void setSexualOrientationId(Integer sexualOrientationId) {
        this.sexualOrientationId = sexualOrientationId;
    }

    public String getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(String sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }
}
