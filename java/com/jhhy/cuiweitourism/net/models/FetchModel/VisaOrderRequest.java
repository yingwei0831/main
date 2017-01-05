package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 2017/1/4.
 * 签证下订单请求
 */
public class VisaOrderRequest extends BasicFetchModel {

    /**
     * memberid : 6
     * productname : 韩国旅游签证
     * price : 300
     * usedate : 2016-10-1
     * dingnum : 2
     * linkman : 张三
     * linktel : 1520656911
     * linkemail : A@a.com
     */

    private String memberid;
    private String productname;
    private String price;
    private String usedate;
    private String dingnum;
    private String linkman;
    private String linktel;
    private String linkemail;

    public VisaOrderRequest(String memberid, String productname, String price, String usedate, String dingnum, String linkman, String linktel, String linkemail) {
        this.memberid = memberid;
        this.productname = productname;
        this.price = price;
        this.usedate = usedate;
        this.dingnum = dingnum;
        this.linkman = linkman;
        this.linktel = linktel;
        this.linkemail = linkemail;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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
}
