package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 2017/1/3.
 * 酒店取消订单
 */
public class HotelOrderCancelRequest extends BasicFetchModel {

    private String OrderNo;
    private String PlatOrderNo;
    private String CancelCode;
    private String Reason;

    /**
     * OrderNo :            易购订单号
     * PlatOrderNo :        供应商订单号
     * CancelCode :         取消原因类型
     * Reason :             取消原因
     */
    public HotelOrderCancelRequest(String orderNo, String platOrderNo, String cancelCode, String reason) {
        OrderNo = orderNo;
        PlatOrderNo = platOrderNo;
        CancelCode = cancelCode;
        Reason = reason;
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

    public String getCancelCode() {
        return CancelCode;
    }

    public void setCancelCode(String CancelCode) {
        this.CancelCode = CancelCode;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }
}
