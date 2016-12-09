package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/11/23.
 */
public class PlaneOrderOfChinaResponse implements Serializable{
    /**
     * ordersn : 82664211089381
     * totalprice : 422
     */

    private String ordersn;
    private float totalprice;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public float getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(float totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public String toString() {
        return "PlaneOrderOfChinaResponse{" +
                "ordersn='" + ordersn + '\'' +
                ", totalprice=" + totalprice +
                '}';
    }
}
