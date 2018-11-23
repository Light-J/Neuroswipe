package com.nsa.cubric.application.domain;

public class Image {

    private int id;
    private String filename;

    public Image(int id, String filename) {
        this.id = id;
        this.filename = filename;
    }

    public String getFilename() {return filename;}
    public int getId() {return id;}
}