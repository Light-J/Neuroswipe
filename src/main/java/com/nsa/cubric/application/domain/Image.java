package com.nsa.cubric.application.domain;

public class Image {
    private Long id;
    private String filename;

    public Image(Long id, String filename) {
        this.id = id;
        this.filename = filename;
    }
}