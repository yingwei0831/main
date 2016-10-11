package com.jhhy.cuiweitourism.net.models.FetchModel;

import com.jhhy.cuiweitourism.net.biz.BasicActionBiz;

/**
 * Created by birney1 on 16/10/10.
 */
public class HotelOrderFetch extends BasicFetchModel {
//    {
//        "memberid": "1",
//            "productaid": "4",
//            "productname": "九寨沟喜来登大酒店",
//            "price": "800",
//            "usedate": "2016-10-10",
//            "dingnum": "1",
//            "linkman": "张三",
//            "linktel": "15210656332",
//            "linkemail": "",
//            "suitid": "5",
//            "departdate": "2016-10-20"
//    }

    public String memberid;
    public String productaid;
    public String productname;
    public String price;
    public String usedate;
    public String dingnum;
    public String linkman;
    public String linktel;
    public String linkemail;
    public String suitid;
    public String departdate;

    public HotelOrderFetch(String memberid, String productaid, String productname, String price, String usedate, String dingnum, String linkman, String linktel, String linkemail, String suitid, String departdate) {
        this.memberid = memberid;
        this.productaid = productaid;
        this.productname = productname;
        this.price = price;
        this.usedate = usedate;
        this.dingnum = dingnum;
        this.linkman = linkman;
        this.linktel = linktel;
        this.linkemail = linkemail;
        this.suitid = suitid;
        this.departdate = departdate;
    }

    public HotelOrderFetch() {
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getProductaid() {
        return productaid;
    }

    public void setProductaid(String productaid) {
        this.productaid = productaid;
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

    public String getUsedate() {
        return usedate;
    }

    public void setUsedate(String usedate) {
        this.usedate = usedate;
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

    public String getLinkemail() {
        return linkemail;
    }

    public void setLinkemail(String linkemail) {
        this.linkemail = linkemail;
    }

    public String getSuitid() {
        return suitid;
    }

    public void setSuitid(String suitid) {
        this.suitid = suitid;
    }

    public String getDepartdate() {
        return departdate;
    }

    public void setDepartdate(String departdate) {
        this.departdate = departdate;
    }
}
