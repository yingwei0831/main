package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/8.
 */
public class CarOrderQuery extends  BasicFetchModel {

    public String PlatOrderNo;
    public String OrderNo;

    public CarOrderQuery(String platOrderNo, String orderNo) {
        PlatOrderNo = platOrderNo;
        OrderNo = orderNo;
    }

    public CarOrderQuery() {
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
}
