package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by birney on 2016-10-02.
 */
public class CarRentOrderResponse implements Serializable{

    //:{"productname":"金龙客车(55座)","ordersn":"03420733570178","price":"2500","usetime":"2016-08-30"}
    public String productname;
    public String ordersn;
    public String price;
    public String usetime;

    public CarRentOrderResponse(String productname, String ordersn, String price, String usetime) {
        this.productname = productname;
        this.ordersn = ordersn;
        this.price = price;
        this.usetime = usetime;
    }

    public CarRentOrderResponse() {
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    @Override
    public String toString() {
        return "CarRentOrderResponse{" +
                "productname='" + productname + '\'' +
                ", ordersn='" + ordersn + '\'' +
                ", price='" + price + '\'' +
                ", usetime='" + usetime + '\'' +
                '}';
    }
}
