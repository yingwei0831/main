package com.jhhy.cuiweitourism.model;

/**
 * Created by jiahe008 on 2016/9/14.
 * 价格区间
 */
public class PriceArea {

    private String priceLower;
    private String priceHigh;

    public PriceArea(String priceLower, String priceHigh) {
        this.priceLower = priceLower;
        this.priceHigh = priceHigh;
    }

    public String getPriceLower() {
        return priceLower;
    }

    public void setPriceLower(String priceLower) {
        this.priceLower = priceLower;
    }

    public String getPriceHigh() {
        return priceHigh;
    }

    public void setPriceHigh(String priceHigh) {
        this.priceHigh = priceHigh;
    }
}
