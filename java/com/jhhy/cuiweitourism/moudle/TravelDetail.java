package com.jhhy.cuiweitourism.moudle;

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
    private String remark; //备注beizhu
    private String priceInclude; //费用包含feeinclude
    private String features; //features
    private String transport; //交通工具
    private String lineDetails; //线路详情xlxq
    private String priceNotContain; //费用不含fybubh
    private List<String> pictureList; //图片列表piclist
    private String businessId; //商家id supplierlist
    private String needScore; //需要积分needjifen
    private List<TravelDetailDay> tripDescribe; //行程描述xcms
    private UserComment comment;
    private String typeId;
    private String commentCount; //评论总数

    public TravelDetail() {
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

    public String getPriceInclude() {
        return priceInclude;
    }

    public void setPriceInclude(String priceInclude) {
        this.priceInclude = priceInclude;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getLineDetails() {
        return lineDetails;
    }

    public void setLineDetails(String lineDetails) {
        this.lineDetails = lineDetails;
    }

    public String getPriceNotContain() {
        return priceNotContain;
    }

    public void setPriceNotContain(String priceNotContain) {
        this.priceNotContain = priceNotContain;
    }

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
}
