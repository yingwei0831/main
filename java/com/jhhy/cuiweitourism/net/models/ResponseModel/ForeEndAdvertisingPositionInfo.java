package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ForeEndAdvertisingPositionInfo {

//    "t": [
//    "http://www.cwly1118.com/uploads/2016/0607/0f59a6280f041ee9a4493358160f7cb6.jpg",
//    "http://www.cwly1118.com/uploads/2016/0607/0f59a6280f041ee9a4493358160f7cb6.jpg"
//    ],
//    "l": [
//    "a1",
//    "b1"
//    ]

    public ArrayList<String> t;
    public ArrayList<String> l;

    public ForeEndAdvertisingPositionInfo(ArrayList<String> t, ArrayList<String> l) {
        this.t = t;
        this.l = l;
    }

    public ForeEndAdvertisingPositionInfo() {
    }

    public ArrayList<String> getT() {
        return t;
    }

    public void setT(ArrayList<String> t) {
        this.t = t;
    }

    public ArrayList<String> getL() {
        return l;
    }

    public void setL(ArrayList<String> l) {
        this.l = l;
    }

    @Override
    public String toString() {
        return "ForeEndAdvertisingPositionInfo{" +
                "t=" + t +
                ", l=" + l +
                '}';
    }
}
