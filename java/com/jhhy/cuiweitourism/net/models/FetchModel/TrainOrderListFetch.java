package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birneysky on 16/10/11.
 * 火车票订单请求
 */
public class TrainOrderListFetch extends BasicFetchModel {
//    "OrderNo":"","PlatOrderNo":""
    private String OrderNo;
    private String PlatOrderNo;

    public TrainOrderListFetch() {
    }

    public TrainOrderListFetch(String orderNo, String platOrderNo) {
        OrderNo = orderNo;
        PlatOrderNo = platOrderNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        PlatOrderNo = platOrderNo;
    }
}
