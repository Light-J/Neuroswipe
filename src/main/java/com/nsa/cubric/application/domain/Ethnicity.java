package com.nsa.cubric.application.domain;

public class Ethnicity {
    Integer ethnicityId;
    String ethnicity;

    public Ethnicity(Integer ethnicityId, String ethnicity) {
        this.ethnicityId = ethnicityId;
        this.ethnicity = ethnicity;
    }

    public Integer getEthnicityId() {
        return ethnicityId;
    }

    public void setEthnicityId(Integer ethnicityId) {
        this.ethnicityId = ethnicityId;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }
}
