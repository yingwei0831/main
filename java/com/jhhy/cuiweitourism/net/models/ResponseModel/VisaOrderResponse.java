package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney1 on 2017/1/4.
 * 签证下订单返回结果
 */
public class VisaOrderResponse {

    /**
     * ordersn : 08391425077410
     * productname : 韩国旅游签证
     * price : 600
     */

    private String ordersn;
    private String productname;
    private String price;

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
}
