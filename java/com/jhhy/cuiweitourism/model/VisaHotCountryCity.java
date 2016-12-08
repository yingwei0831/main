package com.jhhy.cuiweitourism.model;

/**
 * Created by jiahe008 on 2016/9/27.
 */
public class VisaHotCountryCity {
//                    {
//                        "id": "3",
//                        "cityid": "1",
//                        "title": "韩国旅游签证",
//                        "handleday": "预计15个工作日",
//                        "price": "300",
//                        "cityname": "北京"
//                    }

    private String id; //详情用的
    private String cityID; //不管
    private String title;
    private String handleDay;
    private String price;
    private String cityName;

    public VisaHotCountryCity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHandleDay() {
        return handleDay;
    }

    public void setHandleDay(String handleDay) {
        this.handleDay = handleDay;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
