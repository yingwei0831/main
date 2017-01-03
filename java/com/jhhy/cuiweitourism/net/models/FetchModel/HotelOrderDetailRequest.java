package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 2017/1/3.
 * 酒店订单详情
 */
public class HotelOrderDetailRequest extends BasicFetchModel {

    /**
     * OrderNo :        易购订单号
     * PlatOrderNo :    商家订单号
     */

    private String OrderNo;
    private String PlatOrderNo;

    public HotelOrderDetailRequest(String orderNo, String platOrderNo) {
        OrderNo = orderNo;
        PlatOrderNo = platOrderNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String PlatOrderNo) {
        this.PlatOrderNo = PlatOrderNo;
    }
}
