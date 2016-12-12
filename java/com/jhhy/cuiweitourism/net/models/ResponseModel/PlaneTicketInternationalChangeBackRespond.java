package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/12/9.
 * 国际机票退改签说明返回数据
 */
public class PlaneTicketInternationalChangeBackRespond implements Serializable{


    /**
     * zc : 最短停留:无限制最长停留:无限制。退改规定:1.退票:中国始发-起飞前：允许，收取手续费500人民币。起飞后：不允许。泰国始发-起飞前：允许，收取手续费2000泰铢。起飞后：不允许。2.更改:中国始发-允许，收取手续费500人民币。泰国始发-允许，收取手续费2000泰铢。3.误机:无限制。
     */

    private String zc;

    public String getZc() {
        return zc;
    }

    public void setZc(String zc) {
        this.zc = zc;
    }
}
