package com.nsa.cubric.application.domain;

public class Religion {
    Integer religionId;
    String religion;


    public Religion(Integer religionId, String religion) {
        this.religionId = religionId;
        this.religion = religion;
    }

    public Integer getReligionId() {
        return religionId;
    }

    public void setReligionId(Integer religionId) {
        this.religionId = religionId;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }
}
