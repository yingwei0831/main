package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2017/1/6.
 * 支付后，第三方平台下单返回数据
 */
public class TrainOrderFromPlatformResponse {

    /**
     * OrderNo : 7010613350069274   易购云平台id
     * PlatCode : ES                易购云平台
     * PlatOrderNo : EXHC170106133553918    12306订单号
     */

    private String OrderNo;
    private String PlatCode;
    private String PlatOrderNo;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPlatCode() {
        return PlatCode;
    }

    public void setPlatCode(String PlatCode) {
        this.PlatCode = PlatCode;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String PlatOrderNo) {
        this.PlatOrderNo = PlatOrderNo;
    }
}
