package com.jhhy.cuiweitourism.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/5.
 */
public class Order implements Serializable{


    private String id;
    private String productName; //旅游名称
    private String productId; //旅游线路id
    private String price; //订单/旅游价格
    private String addTime; //添加时间
    private String status; //订单状态
    private String statusComment; //是否评论
    private String picPath; //首页图片地址

    private String orderSN; //提交订单后，产生的
    private String adultNum; //成人数量
    private String childNum; //儿童数量
    private String isPay; //付款状态 0：未付款
    private String linkMan; //联系人
    private String linkMobile; //联系电话
    private String needScore; //最多使用积分/旅游币
    private List<UserContacts> travelers; //游客
    private Invoice invoice; //发票
    private String useTravelIcon; //使用的积分/旅游币

    private String typeId; //类型： 1.线路、2.酒店、3租车、8签证、14私人定制
    private String productautoid; //线路id

    private String startaddress; //订单：租车订单
    private String endaddress; //订单：租车订单

    private String sanfangorderno; //第三方订单号；国内机票支付过了就有，国际机票只有买到机票才有，签证没有，酒店下订单后就有

    //isneedpiao,dingnum,childnum,oldnum,ispay,needjifen,status,jifentprice,youke,lvyoubi,

    public Order() {
    }

    public String getSanfangorderno() {
        return sanfangorderno;
    }

    public void setSanfangorderno(String sanfangorderno) {
        this.sanfangorderno = sanfangorderno;
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getProductautoid() {
        return productautoid;
    }

    public void setProductautoid(String productautoid) {
        this.productautoid = productautoid;
    }

    public String getUseTravelIcon() {
        return useTravelIcon;
    }

    public void setUseTravelIcon(String useTravelIcon) {
        this.useTravelIcon = useTravelIcon;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public List<UserContacts> getTravelers() {
        return travelers;
    }

    public void setTravelers(List<UserContacts> travelers) {
        this.travelers = travelers;
    }

    public String getNeedScore() {
        return needScore;
    }

    public void setNeedScore(String needScore) {
        this.needScore = needScore;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkMobile() {
        return linkMobile;
    }

    public void setLinkMobile(String linkMobile) {
        this.linkMobile = linkMobile;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getAdultNum() {
        return adultNum;
    }

    public void setAdultNum(String adultNum) {
        this.adultNum = adultNum;
    }

    public String getChildNum() {
        return childNum;
    }

    public void setChildNum(String childNum) {
        this.childNum = childNum;
    }

    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusComment() {
        return statusComment;
    }

    public void setStatusComment(String statusComment) {
        this.statusComment = statusComment;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productId='" + productId + '\'' +
                ", price='" + price + '\'' +
                ", addTime='" + addTime + '\'' +
                ", status='" + status + '\'' +
                ", statusComment='" + statusComment + '\'' +
                ", picPath='" + picPath + '\'' +
                ", orderSN='" + orderSN + '\'' +
                ", adultNum='" + adultNum + '\'' +
                ", childNum='" + childNum + '\'' +
                ", isPay='" + isPay + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", linkMobile='" + linkMobile + '\'' +
                ", needScore='" + needScore + '\'' +
                ", travelers=" + travelers +
                ", invoice=" + invoice +
                ", useTravelIcon='" + useTravelIcon + '\'' +
                ", typeId='" + typeId + '\'' +
                ", productautoid='" + productautoid + '\'' +
                '}';
    }
}
