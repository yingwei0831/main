package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by zhangguang on 16/10/19.
 */
public class TrainTicketOrderInfo implements Serializable{
//    "ordersn": "80572124935477",
//    "productname": "购买火车票_200",
//    "price": "243"

    public String ordersn;
    public String productname;
    public String price;

    public TrainTicketOrderInfo(String ordersn, String productname, String price) {
        this.ordersn = ordersn;
        this.productname = productname;
        this.price = price;
    }

    public TrainTicketOrderInfo() {
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
        return "TrainTicketOrderInfo{" +
                "ordersn='" + ordersn + '\'' +
                ", productname='" + productname + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
