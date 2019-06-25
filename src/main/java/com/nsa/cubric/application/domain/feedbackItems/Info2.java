package com.nsa.cubric.application.domain.feedbackItems;

public class Info2 {
    // Info 2 options can be
    //   - Not at all
    //   - Not really
    //   - Yes, mostly
    //   - Yes definitely

    Integer notAtAll;
    Integer notReally;
    Integer yesMostly;
    Integer yesDefinitely;

    public Info2() {
        this.notAtAll = 0;
        this.notReally = 0;
        this.yesMostly = 0;
        this.yesDefinitely = 0;
    }

    public void addFigures(String result){
        switch (result){
            case "not at all":
                this.notAtAll = this.notAtAll + 1;
                break;
            case "not really":
                this.notReally = this.notReally + 1;
                break;
            case "yes mostly":
                this.yesMostly = this.yesMostly + 1;
                break;
            case "yes definitely":
                this.yesDefinitely = this.yesDefinitely + 1;
                break;
        }
    }

    public Integer getNotAtAll() {
        return notAtAll;
    }

    public Integer getNotReally() {
        return notReally;
    }

    public Integer getYesMostly() {
        return yesMostly;
    }

    public Integer getYesDefinitely() {
        return yesDefinitely;
    }
}
