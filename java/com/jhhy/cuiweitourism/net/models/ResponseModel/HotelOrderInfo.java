package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney1 on 16/10/10.
 */
public class HotelOrderInfo {

    public String ordersn;
    public String productname;
    public String  price;

    public HotelOrderInfo(String ordersn, String productname, String price) {
        this.ordersn = ordersn;
        this.productname = productname;
        this.price = price;
    }

    public HotelOrderInfo() {
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "HotelOrderInfo{" +
                "ordersn='" + ordersn + '\'' +
                ", productname='" + productname + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
