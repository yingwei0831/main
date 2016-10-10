package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HomePageOfflinePay extends BasicFetchModel {

    public String orderid;

    public HomePageOfflinePay(String orderid) {
        this.orderid = orderid;
    }

    public HomePageOfflinePay() {
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }
}
