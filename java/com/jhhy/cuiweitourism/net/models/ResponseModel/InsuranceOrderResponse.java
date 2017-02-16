package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by jiahe008_lvlanlan on 2017/2/16.
 */
public class InsuranceOrderResponse  implements Serializable{

    /**
     * ordersn : 07297251576467
     * productname : 国内游10元套餐
     * price : 11
     */

    private String ordersn;
    private String productname;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
