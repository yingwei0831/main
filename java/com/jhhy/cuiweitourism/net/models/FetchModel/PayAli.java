package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/10/28.
 */
public class PayAli extends BasicFetchModel {
//    {"head":{"code":"Alipay_index"},"field":{"ordersn":"80489619661756"}}
    private String ordersn;

    public PayAli() {
    }

    public PayAli(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    @Override
    public String toString() {
        return "PayAli{" +
                "ordersn='" + ordersn + '\'' +
                '}';
    }
}
