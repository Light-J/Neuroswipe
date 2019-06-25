package com.nsa.cubric.application.domain.feedbackItems;

public class Sorting {
    // Sorting options can be
    //    - Really easy
    //    - Quite easy
    //    - Quite difficult
    //    - Really difficult

    Integer reallyEasy;
    Integer quiteEasy;
    Integer quiteDifficult;
    Integer reallyDifficult;

    public Sorting() {
        this.reallyEasy = 0;
        this.quiteDifficult = 0;
        this.quiteEasy = 0;
        this.reallyDifficult = 0;
    }

    public void addFigures(String result){
        switch (result){
            case "really difficult":
                this.reallyDifficult = this.reallyDifficult + 1;
                break;
            case "quite difficult":
                this.quiteDifficult = this.quiteDifficult + 1;
                break;
            case "quite easy":
                this.quiteEasy = this.quiteEasy + 1;
                break;
            case "really easy":
                this.reallyEasy = this.reallyEasy + 1;
                break;
        }
    }

    public Integer getReallyEasy() {
        return reallyEasy;
    }

    public Integer getQuiteEasy() {
        return quiteEasy;
    }

    public Integer getQuiteDifficult() {
        return quiteDifficult;
    }

    public Integer getReallyDifficult() {
        return reallyDifficult;
    }
}
