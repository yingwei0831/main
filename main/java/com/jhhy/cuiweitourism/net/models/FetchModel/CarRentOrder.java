package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-02.
 */
public class CarRentOrder extends BasicFetchModel {

    //{"memberid":"6","carid":"7","days":"2","startaddress":"北京市昌平区史各庄",
    // "endaddress":"辽宁省凌源市火车站","usetime":"2016-08-30",
    // "price":"2500","linkman":"张三","linktel":"15210656332",
    // "productname":"金龙客车(55座)"}
    public String memberid;
    public String carid;
    public String days;
    public String startaddress;
    public String endaddress;
    public String usetime;
    public String price;
    public String linkman;
    public String linktel;
    public String productname;


    public CarRentOrder(String memberid, String carid, String days, String startaddress, String endaddress, String usetime, String price, String linkman, String linktel, String productname) {
        this.memberid = memberid;
        this.carid = carid;
        this.days = days;
        this.startaddress = startaddress;
        this.endaddress = endaddress;
        this.usetime = usetime;
        this.price = price;
        this.linkman = linkman;
        this.linktel = linktel;
        this.productname = productname;
    }

    public CarRentOrder() {
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getStartaddress() {
        return startaddress;
    }

    public void setStartaddress(String startaddress) {
        this.startaddress = startaddress;
    }

    public String getEndaddress() {
        return endaddress;
    }

    public void setEndaddress(String endaddress) {
        this.endaddress = endaddress;
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
}
