package com.jhhy.cuiweitourism.model;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/9/21.
 * 发票
 */
public class Invoice implements Serializable{
    private String title; //发票抬头
    private String receiver; //发票接收人
    private String mobile; //发票接收人电话
    private String address; //发票接收人地址

    public Invoice() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
