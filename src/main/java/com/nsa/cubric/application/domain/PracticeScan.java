package com.nsa.cubric.application.domain;

public class PracticeScan extends Scan {
    Boolean isImageCorrect;
    public PracticeScan(int id, String path1, String path2, String path3, boolean isImageCorrect) {
        super(id, path1, path2, path3, null);
        this.isImageCorrect = isImageCorrect;
    }

    public Boolean getImageCorrect() {
        return isImageCorrect;
    }

    public void setImageCorrect(Boolean imageCorrect) {
        isImageCorrect = imageCorrect;
    }
}
