package com.jhhy.cuiweitourism.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiahe008 on 2016/9/18.
 * 国内游详情页
 */
public class TravelDetail implements Serializable{

    private String id;
    private String title;
    private String price;
//    private String priceInclude; //费用包含feeinclude
//    private String priceNotContain; //费用不含fybubh
//    private String features; //features
//    private String transport; //交通工具
    private String lineDetails; //线路特色xlxq
    private List<TravelDetailDay> tripDescribe; //行程安排xcms
    private String standard; //标准
    private String remark; //预订须知beizhu
    private List<String> pictureList; //图片列表piclist
    private String businessId; //商家id supplierlist
    private String needScore; //需要积分needjifen
    private UserComment comment;
    private String typeId;
    private String commentCount; //评论总数

    private String im; //商户环信账号
    private String iscollect; //1：已收藏；0：未收藏


    public TravelDetail() {
    }

    public String getIscollect() {
        return iscollect;
    }

    public void setIscollect(String iscollect) {
        this.iscollect = iscollect;
    }

    public String getIm() {
        return im;
    }

    public void setIm(String im) {
        this.im = im;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

//    public String getPriceInclude() {
//        return priceInclude;
//    }
//
//    public void setPriceInclude(String priceInclude) {
//        this.priceInclude = priceInclude;
//    }
//
//    public String getFeatures() {
//        return features;
//    }
//
//    public void setFeatures(String features) {
//        this.features = features;
//    }
//
//    public String getTransport() {
//        return transport;
//    }
//
//    public void setTransport(String transport) {
//        this.transport = transport;
//    }

    public String getLineDetails() {
        return lineDetails;
    }

    public void setLineDetails(String lineDetails) {
        this.lineDetails = lineDetails;
    }

//    public String getPriceNotContain() {
//        return priceNotContain;
//    }
//
//    public void setPriceNotContain(String priceNotContain) {
//        this.priceNotContain = priceNotContain;
//    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getNeedScore() {
        return needScore;
    }

    public void setNeedScore(String needScore) {
        this.needScore = needScore;
    }

    public List<TravelDetailDay> getTripDescribe() {
        return tripDescribe;
    }

    public void setTripDescribe(List<TravelDetailDay> tripDescribe) {
        this.tripDescribe = tripDescribe;
    }

    public UserComment getComment() {
        return comment;
    }

    public void setComment(UserComment comment) {
        this.comment = comment;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    @Override
    public String toString() {
        return "TravelDetail{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", lineDetails='" + lineDetails + '\'' +
                ", tripDescribe=" + tripDescribe +
                ", standard='" + standard + '\'' +
                ", remark='" + remark + '\'' +
                ", pictureList=" + pictureList +
                ", businessId='" + businessId + '\'' +
                ", needScore='" + needScore + '\'' +
                ", comment=" + comment +
                ", typeId='" + typeId + '\'' +
                ", commentCount='" + commentCount + '\'' +
                ", im='" + im + '\'' +
                ", iscollect='" + iscollect + '\'' +
                '}';
    }

}
