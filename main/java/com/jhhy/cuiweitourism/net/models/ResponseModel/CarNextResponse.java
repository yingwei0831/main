package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney on 2016-10-01.
 */
public class CarNextResponse {
    //{"wfjl":772.052,"price":3750}
    public  String  wfjl;
    public  String  price;

    public CarNextResponse(String wfjl, String price) {
        this.wfjl = wfjl;
        this.price = price;
    }

    public CarNextResponse() {
    }

    public String getWfjl() {
        return wfjl;
    }

    public void setWfjl(String wfjl) {
        this.wfjl = wfjl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "CarNextResponse{" +
                "wfjl='" + wfjl + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
