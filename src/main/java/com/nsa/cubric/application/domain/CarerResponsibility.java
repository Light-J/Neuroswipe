package com.nsa.cubric.application.domain;

public class CarerResponsibility {
    int id;
    String responsibility;

    public CarerResponsibility(int id, String responsibility) {
        this.id = id;
        this.responsibility = responsibility;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }
}
