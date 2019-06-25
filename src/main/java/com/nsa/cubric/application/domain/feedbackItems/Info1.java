package com.nsa.cubric.application.domain.feedbackItems;

public class Info1{
    Integer tooMuch;
    Integer aboutRight;
    Integer tooLittle;

    public Info1() {
        this.tooMuch = 0;
        this.aboutRight = 0;
        this.tooLittle = 0;
    }

    public void addFigures(String result){
        switch (result){
            case "not enough information":
                this.tooLittle = this.tooLittle+1;
                break;
            case "about the right amount of information":
                this.aboutRight = this.aboutRight + 1;
                break;
            case "too much information":
                this.tooMuch = this.tooMuch + 1;
                break;
        }
    }

    public Integer getTooMuch() {
        return tooMuch;
    }

    public Integer getAboutRight() {
        return aboutRight;
    }

    public Integer getTooLittle() {
        return tooLittle;
    }
}
