package com.nsa.cubric.application.domain;

public class Image {

    private int id;
    private String path;
    private Boolean knownGood;

    public Image(int id, String path, Boolean knownGood) {
        this.id = id;
        this.path = path;
        this.knownGood = knownGood;
    }

    public String getPath() {return path;}
    public int getId() {return id;}
    public Boolean getKnownGood() {return knownGood;}

    public void setPath(String path) { this.path = path; }
    public void setId(int id) { this.id = id; }
    public void setKnownGood(Boolean knownGood) {this.knownGood = knownGood;}
}