package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by birney1 on 2017/1/4.
 * 签证下订单返回结果
 */
public class VisaOrderResponse implements Serializable{
//    {"head":{"res_code":"0000","res_msg":"success","res_arg":"订单添加成功"},"body":{"ordersn":"08808852092934","productname":"英国旅游签证[北京领区]三人套餐","price":6000}}

    /**
     * ordersn : 08391425077410
     * productname : 韩国旅游签证
     * price : 600
     */

    private String ordersn;
    private String productname;
    private String price;

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
