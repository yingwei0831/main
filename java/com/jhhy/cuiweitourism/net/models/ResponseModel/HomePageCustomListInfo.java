package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageCustomListInfo {
    //{"aid":"1","title":"优秀定制：夏威夷海岛7日游","litpic":"http:\/\/cwly.taskbees.cn:8083\/uploads\/2016\/0812\/e1d960d3e5d595411012a1fff8154dfd.jpg",
    // "price":"70000","mdd":"夏威夷","days":"10"}
    public String aid;
    public String title;
    public String litpic;
    public String price;
    public String mdd;
    public String days;

    public HomePageCustomListInfo(String aid, String title, String litpic, String price, String mdd, String days) {
        this.aid = aid;
        this.title = title;
        this.litpic = litpic;
        this.price = price;
        this.mdd = mdd;
        this.days = days;
    }

    public HomePageCustomListInfo() {
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

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMdd() {
        return mdd;
    }

    public void setMdd(String mdd) {
        this.mdd = mdd;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    @Override
    public String toString() {
        return "HomePageCustomListInfo{" +
                "aid='" + aid + '\'' +
                ", title='" + title + '\'' +
                ", litpic='" + litpic + '\'' +
                ", price='" + price + '\'' +
                ", mdd='" + mdd + '\'' +
                ", days='" + days + '\'' +
                '}';
    }
}
