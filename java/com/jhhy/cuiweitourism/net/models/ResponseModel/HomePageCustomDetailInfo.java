package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageCustomDetailInfo {
    //{"aid":"1","title":"优秀定制：夏威夷海岛7日游",
    // "piclist":[
    //  "http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0812\/e1d960d3e5d595411012a1fff8154dfd.jpg",
    // "http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0715\/316c62e7521cb1bf4c67ea1ff1560841.jpg",
    // "http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0715\/b70f7dc92e81780c9b6dccbe50658a05.jpg",
    // "http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0607\/0f59a6280f041ee9a4493358160f7cb6.jpg"],
    // "price":"70000","content":"<p>夏威夷海岛蜜月游<br\/><\/p>","finaldestid":"46",
    // "typeid":"203","area":"夏威夷","xcts":"10","xcms":"<p>xingcheng pai <br\/><\/p>"}

    public String aid;
    public String title;
    public ArrayList<String> piclist;
    public String price;
    public String content;
    public String finaldestid;
    public String typeid;
    public String area;
    public String xcts;
    public String xcms;

    public HomePageCustomDetailInfo(String aid, String title, ArrayList<String> piclist, String price, String content, String finaldestid, String typeid, String area, String xcts, String xcms) {
        this.aid = aid;
        this.title = title;
        this.piclist = piclist;
        this.price = price;
        this.content = content;
        this.finaldestid = finaldestid;
        this.typeid = typeid;
        this.area = area;
        this.xcts = xcts;
        this.xcms = xcms;
    }

    public HomePageCustomDetailInfo() {
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPiclist() {
        return piclist;
    }

    public void setPiclist(ArrayList<String> piclist) {
        this.piclist = piclist;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFinaldestid() {
        return finaldestid;
    }

    public void setFinaldestid(String finaldestid) {
        this.finaldestid = finaldestid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getXcts() {
        return xcts;
    }

    public void setXcts(String xcts) {
        this.xcts = xcts;
    }

    public String getXcms() {
        return xcms;
    }

    public void setXcms(String xcms) {
        this.xcms = xcms;
    }

    @Override
    public String toString() {
        return "HomePageCustomDetailInfo{" +
                "aid='" + aid + '\'' +
                ", title='" + title + '\'' +
                ", piclist=" + piclist +
                ", price='" + price + '\'' +
                ", content='" + content + '\'' +
                ", finaldestid='" + finaldestid + '\'' +
                ", typeid='" + typeid + '\'' +
                ", area='" + area + '\'' +
                ", xcts='" + xcts + '\'' +
                ", xcms='" + xcms + '\'' +
                '}';
    }
}
