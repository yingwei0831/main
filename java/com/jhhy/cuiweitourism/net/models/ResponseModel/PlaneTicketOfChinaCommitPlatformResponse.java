package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2017/1/8.
 * 国内机票下单到平台
 */
public class PlaneTicketOfChinaCommitPlatformResponse {

    /**
     * OrderNo : 112017010883345993
     * pnrNo : KNPDJX
     * OrderStatus : NEW_ORDER
     * param1 : 40.0
     */

    private String OrderNo;
    private String pnrNo;
    private String OrderStatus;
    private String param1;

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }
}
