package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/5.
 * 火车票支付完，下单到第三方
 */
public class TrainOrderToOtherPlatRequest extends BasicFetchModel{


    /**
     * ordersn :
     */

    private String ordersn;

    public TrainOrderToOtherPlatRequest(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }
}
