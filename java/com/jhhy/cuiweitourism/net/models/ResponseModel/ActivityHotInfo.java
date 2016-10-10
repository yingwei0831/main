package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney on 2016-10-09.
 */
public class ActivityHotInfo {
    //id":"1","litpic":"http:\/\/www.cwly1118.com\/uploads\/2015\/0909\/6576cb3bfd961a0e13e6cc203b4ce012.jpg",
    // "title":"撒哈拉沙漠","price":"50000.00","bmrs":"1"}
    public String id;
    public String litpic;
    public String title;
    public String price;
    public String bmrs;

    public ActivityHotInfo() {
    }

    public ActivityHotInfo(String id, String litpic, String title, String price, String bmrs) {

        this.id = id;
        this.litpic = litpic;
        this.title = title;
        this.price = price;
        this.bmrs = bmrs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBmrs() {
        return bmrs;
    }

    public void setBmrs(String bmrs) {
        this.bmrs = bmrs;
    }

    @Override
    public String toString() {
        return "ActivityHotInfo{" +
                "id='" + id + '\'' +
                ", litpic='" + litpic + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", bmrs='" + bmrs + '\'' +
                '}';
    }
}
