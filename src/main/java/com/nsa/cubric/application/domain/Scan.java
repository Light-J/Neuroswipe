package com.nsa.cubric.application.domain;

public class Scan {

    private int id;
    private String path1;
    private String path2;
    private String path3;
    private Boolean knownGood;

    public Scan(int id, String path1, String path2, String path3, Boolean knownGood) {
        this.id = id;
        this.path1 = path1;
        this.path2 = path2;
        this.path3 = path3;
        this.knownGood = knownGood;
    }

    public String getPath1() {return path1;}
    public String getPath2() {return path2;}
    public String getPath3() {return path3;}
    public int getId() {return id;}
    public Boolean getKnownGood() {return knownGood;}

    public void setPath1(String path1) { this.path1 = path1;}
    public void setPath2(String path1) { this.path2 = path2;}
    public void setPath3(String path1) { this.path3 = path3;}
    public void setId(int id) { this.id = id; }
    public void setKnownGood(Boolean knownGood) {this.knownGood = knownGood;}
}