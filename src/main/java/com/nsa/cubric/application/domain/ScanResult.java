package com.nsa.cubric.application.domain;

public class ScanResult {
    private String scan;
    private Integer goodRatings;
    private Integer badRatings;

    public ScanResult(String scan, Integer goodRatings, Integer badRatings) {
        this.scan = scan;
        this.goodRatings = goodRatings;
        this.badRatings = badRatings;
    }


    public String[] getCSVFormatted(){
        return new String[]{scan, goodRatings.toString(), badRatings.toString()};
    }
}
