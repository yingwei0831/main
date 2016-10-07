package com.jhhy.cuiweitourism.moudle;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/9/18.
 * 国内游详情页——>行程描述
 */
public class TravelDetailDay implements Serializable{

    private String day; //第一天
    private String title; //标题
    private String detail; //介绍

    public TravelDetailDay() {
    }

    public TravelDetailDay(String day, String title, String detail) {
        this.day = day;
        this.title = title;
        this.detail = detail;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
