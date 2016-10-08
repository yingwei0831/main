package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/8.
 */
public class CarSmallOrderCancel extends  BasicFetchModel {

    //{"PlatOrderNo":"","OrderNo":"","CancelReason":"","Force":""}

    public String  PlatOrderNo;
    public String  OrderNo;
    public String  CancelReason;
    public String  Force;

    public CarSmallOrderCancel(String platOrderNo, String orderNo, String cancelReason, String force) {
        PlatOrderNo = platOrderNo;
        OrderNo = orderNo;
        CancelReason = cancelReason;
        Force = force;
    }

    public CarSmallOrderCancel() {
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        PlatOrderNo = platOrderNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getCancelReason() {
        return CancelReason;
    }

    public void setCancelReason(String cancelReason) {
        CancelReason = cancelReason;
    }

    public String getForce() {
        return Force;
    }

    public void setForce(String force) {
        Force = force;
    }
}
