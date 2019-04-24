package com.nsa.cubric.application.domain;

public class ScanResult {
    private int scanId;
    private int goodRatings;
    private int badRatings;

    public ScanResult(int scanId, int goodRatings, int badRatings) {
        this.scanId = scanId;
        this.goodRatings = goodRatings;
        this.badRatings = badRatings;
    }


    public int getScanId() {
        return scanId;
    }

    public void setScanId(int scanId) {
        this.scanId = scanId;
    }

    public int getGoodRatings() {
        return goodRatings;
    }

    public void setGoodRatings(int goodRatings) {
        this.goodRatings = goodRatings;
    }

    public int getBadRatings() {
        return badRatings;
    }

    public void setBadRatings(int badRatings) {
        this.badRatings = badRatings;
    }
}
