package com.jhhy.cuiweitourism.moudle;

/**
 * Created by jiahe008 on 2016/8/30.
 */
public class CustomTravel {
    private String title;
    private String price;
    private String imgPath;
    private String destinationName; //目的地
    private String days; //天数

    public CustomTravel() {
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

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
