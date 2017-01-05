package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/5.
 * 国内机票取消订单
 */
public class PlaneTicketOfChinaCancelOrderRequest extends BasicFetchModel{

    /**
     * ordersn : 82900924239325
     * memberid : 52
     */

    private String ordersn;
    private String memberid;

    public PlaneTicketOfChinaCancelOrderRequest(String ordersn, String memberid) {
        this.ordersn = ordersn;
        this.memberid = memberid;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }
}
