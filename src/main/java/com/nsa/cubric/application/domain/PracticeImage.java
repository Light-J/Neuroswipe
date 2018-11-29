package com.nsa.cubric.application.domain;

public class PracticeImage extends Image {
    Boolean isImageCorrect;
    public PracticeImage(int id, String filename, boolean isImageCorrect) {
        super(id, filename, null);
        this.isImageCorrect = isImageCorrect;
    }

    public Boolean getImageCorrect() {
        return isImageCorrect;
    }

    public void setImageCorrect(Boolean imageCorrect) {
        isImageCorrect = imageCorrect;
    }
}
