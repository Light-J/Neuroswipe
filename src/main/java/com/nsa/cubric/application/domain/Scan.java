package com.nsa.cubric.application.domain;

public class Scan {

    private int id;
    private String topImage;
    private String sideImage;
    private String frontImage;
    private Boolean knownGood;
    private String reason;

    public Scan(int id, String topImage, String sideImage, String frontImage, Boolean knownGood, String reason) {
        this.id = id;
        this.topImage = topImage;
        this.sideImage = sideImage;
        this.frontImage = frontImage;
        this.knownGood = knownGood;
        this.reason = reason;
    }

    public String getTopImage() {return topImage;}
    public String getSideImage() {return sideImage;}
    public String getFrontImage() {return frontImage;}
    public int getId() {return id;}
    public Boolean getKnownGood() {return knownGood;}
    public String getReason() { return reason; }

    public void setTopImage(String topImage) { this.topImage = topImage;}
    public void setSideImage(String path1) { this.sideImage = sideImage;}
    public void setFrontImage(String path1) { this.frontImage = frontImage;}
    public void setId(int id) { this.id = id; }
    public void setKnownGood(Boolean knownGood) {this.knownGood = knownGood;}
    public void setReason(String reason) { this.reason = reason; }
}