package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ActivityOrder  extends  BasicFetchModel{

    //{"memberid":"6","hid":"7","usetime":"2016-08-30","price":"2500","dingnum":"1","linkman":"张三","linktel":"15210656332",
    // "productname":"****",
    // "lxr":[{"tourername":"王二麻子","cardnumber":"233695898745896597","mobile":"13895878954"},{"tourername":"王三麻子","cardnumber":"233699685748896597","mobile":"13869578954"}]}

   static public class Contact extends BasicFetchModel{
        public String tourername;
        public String cardnumber;
        public String  mobile;

        public Contact(String tourername, String cardnumber, String mobile) {
            this.tourername = tourername;
            this.cardnumber = cardnumber;
            this.mobile = mobile;
        }

        public Contact() {
        }

        public String getTourername() {
            return tourername;
        }

        public void setTourername(String tourername) {
            this.tourername = tourername;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public String memberid;
    public String hid;
    public String usetime;
    public String price;
    public String dingnum;
    public String linkman;
    public String linktel;
    public String productname;
    public ArrayList<Contact> lxr;


    public ActivityOrder(String memberid, String hid, String usetime, String price, String dingnum, String linkman, String linktel, String productname, ArrayList<Contact> lxr) {
        this.memberid = memberid;
        this.hid = hid;
        this.usetime = usetime;
        this.price = price;
        this.dingnum = dingnum;
        this.linkman = linkman;
        this.linktel = linktel;
        this.productname = productname;
        this.lxr = lxr;
    }


    public ActivityOrder() {
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getHid() {
        return hid;
    }

    public void setHid(String hid) {
        this.hid = hid;
    }

    public String getUsetime() {
        return usetime;
    }

    public void setUsetime(String usetime) {
        this.usetime = usetime;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDingnum() {
        return dingnum;
    }

    public void setDingnum(String dingnum) {
        this.dingnum = dingnum;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public ArrayList<Contact> getLxr() {
        return lxr;
    }

    public void setLxr(ArrayList<Contact> lxr) {
        this.lxr = lxr;
    }
}
