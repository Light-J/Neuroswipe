package com.nsa.cubric.application.domain;

public class Image {

    private int id;
    private String path;

    public Image(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public String getPath() {return path;}
    public int getId() {return id;}

    public void setPath(String path) { this.path = path; }
    public void setId(int id) { this.id = id; }
}