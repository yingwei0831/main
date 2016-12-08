package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/19.
 * 价格日历
 */
public class CalendarPrice {
    private String date;
    private String childPrice;
    private String olderPrice;
    private String adultPrice;

    public CalendarPrice() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(String childPrice) {
        this.childPrice = childPrice;
    }

    public String getOlderPrice() {
        return olderPrice;
    }

    public void setOlderPrice(String olderPrice) {
        this.olderPrice = olderPrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(String adultPrice) {
        this.adultPrice = adultPrice;
    }

    public CalendarPrice(String date, String childPrice, String olderPrice, String adultPrice) {
        this.date = date;
        this.childPrice = childPrice;
        this.olderPrice = olderPrice;
        this.adultPrice = adultPrice;
    }
}
