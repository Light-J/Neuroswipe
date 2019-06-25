package com.nsa.cubric.application.domain.feedbackItems;

public class Training {
    // Training options can be
    //    - Definitely not
    //    - Not really
    //    - Yes probably
    //    - Yes definitely

    Integer definitelyNot;
    Integer notReally;
    Integer yesProbably;
    Integer yesDefinitely;

    public Training() {
        this.definitelyNot = 0;
        this.notReally = 0;
        this.yesProbably = 0;
        this.yesDefinitely = 0;
    }

    public void addFigures(String result){
        switch (result){
            case "definitely not":
                this.definitelyNot = this.definitelyNot + 1;
                break;
            case "not really":
                this.notReally = this.notReally + 1;
                break;
            case "yes probably":
                this.yesProbably = this.yesProbably + 1;
                break;
            case "yes definitely":
                this.yesDefinitely = this.yesDefinitely + 1;
                break;
        }
    }

    public Integer getNotAtAll() {
        return definitelyNot;
    }

    public Integer getNotReally() {
        return notReally;
    }

    public Integer getYesMostly() {
        return yesProbably;
    }

    public Integer getYesDefinitely() {
        return yesDefinitely;
    }
}
