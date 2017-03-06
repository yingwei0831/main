package com.jhhy.cuiweitourism.model;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/8/4.
 */
public class Travel implements Serializable
{
    private String id;
    private String travelIconPath;
    private String travelTitle; //
    private String travelPrice;

    private String gysid; //供应商id
    private String im; //环信客服号

    private String travelFromCity;
    private String travelFromTime;
    private String travelPeriod;
    private String travelName; //productName?
    private String type; //1跟团游，2自由游
    private int account; //1人已报名(热门推荐)

    private String group; //环信用
    private String orderSN; //订单sn

    private String sjmc;

    public Travel(){}

    public String getSjmc() {
        return sjmc;
    }

    public void setSjmc(String sjmc) {
        this.sjmc = sjmc;
    }

    public String getGysid() {
        return gysid;
    }

    public void setGysid(String gysid) {
        this.gysid = gysid;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public Travel(String id, String travelIconPath, String travelTitle, String travelPrice, String gysid, String im, String travelFromCity, String travelFromTime, String travelPeriod, String travelName, String type, int account, String group, String orderSN) {
        this.id = id;
        this.travelIconPath = travelIconPath;
        this.travelTitle = travelTitle;
        this.travelPrice = travelPrice;
        this.gysid = gysid;
        this.im = im;
        this.travelFromCity = travelFromCity;
        this.travelFromTime = travelFromTime;
        this.travelPeriod = travelPeriod;
        this.travelName = travelName;
        this.type = type;
        this.account = account;
        this.group = group;
        this.orderSN = orderSN;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTravelPeriod() {
        return travelPeriod;
    }

    public void setTravelPeriod(String travelPeriod) {
        this.travelPeriod = travelPeriod;
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTravelIconPath() {
        return travelIconPath;
    }

    public void setTravelIconPath(String travelIconPath) {
        this.travelIconPath = travelIconPath;
    }

    public String getTravelTitle() {
        return travelTitle;
    }

    public void setTravelTitle(String travelTitle) {
        this.travelTitle = travelTitle;
    }

    public String getTravelPrice() {
        return travelPrice;
    }

    public void setTravelPrice(String travelPrice) {
        this.travelPrice = travelPrice;
    }

    public String getTravelFromCity() {
        return travelFromCity;
    }

    public void setTravelFromCity(String travelFromCity) {
        this.travelFromCity = travelFromCity;
    }

    public String getTravelFromTime() {
        return travelFromTime;
    }

    public void setTravelFromTime(String travelFromTime) {
        this.travelFromTime = travelFromTime;
    }

    public String getTravlePeriod() {
        return travelPeriod;
    }

    public void setTravlePeriod(String travelPeriod) {
        this.travelPeriod = travelPeriod;
    }
}
