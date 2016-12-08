package com.jhhy.cuiweitourism.model;

/**
 * Created by jiahe008 on 2016/9/10.
 * 发起活动，私人活动
 */
public class CustomActivity {
//   {"mid": "1",
//    "litpic": "img",
//    "title": "撒哈拉沙漠自由行",
//    "price": "5000",
//    "cfcity": "北京",
//    "cftime": "2016-10-20",
//    "days": "5",
//    "num": "5",
//    "lineinfo": "线路概述",
//    "xqinfo": "详情描述",
//    "fyinfo": "费用描述",
//    "xcinfo": "行程描述",
//    "ydxz": "预定需知" }
    //我的发布
//{   "id": "5",
//    "litpic": "http://cwly.taskbees.cn:8083/uploads/activity/861473642620.jpg,/uploads/activity/351473642620.jpg",
//    "title": "Yyyyy",
//    "price": "2222.00",
//    "cfcity": "20",
//    "cftime": "1476921600",
//    "days": "12311",
//    "num": "123",
//    "lineinfo": "123123123",
//    "xqinfo": "123123123",
//    "fyinfo": "123123123",
//    "xcinfo": "2131231212312",
//    "ydxz": "123123123",
//    "status": "0",
//    "addtime": "1473642620",
//    "memberid": "1",
//    "ishot": "0"}
    private String id;
    private String activityLitpic; //列表展示图片,1张
    private String[] lipPicAry; //发布活动时图片数组，最多4张
    private String activityTitle;
    private String activityPrice;
    private String activityFromCity; //出发城市
    private String activityFromTime; //出发时间
    private String activityTripDays; //行程天数
    private String activityGroupAccount; //最多参团人数
    private String activityLineDetails; //线路概况
    private String activityDetailDescribe; //详情描述
    private String activityPriceDescribe; //费用描述
    private String activityTripDescribe; //行程描述,第一天。。第二天。。。第三天。。。
    private String activityReserveNotice; //预订须知

    private String actyStatus;
    private String actyAddTime;
    private String actyMemberId;
    private String actyIsHot;

    private boolean isSelection; //用于发布列表中，选择，删除功能

    public CustomActivity() {
    }

    public String[] getLipPicAry() {
        return lipPicAry;
    }

    public void setLipPicAry(String[] lipPicAry) {
        this.lipPicAry = lipPicAry;
    }

    public boolean isSelection() {
        return isSelection;
    }

    public void setSelection(boolean selection) {
        isSelection = selection;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActyStatus() {
        return actyStatus;
    }

    public void setActyStatus(String actyStatus) {
        this.actyStatus = actyStatus;
    }

    public String getActyAddTime() {
        return actyAddTime;
    }

    public void setActyAddTime(String actyAddTime) {
        this.actyAddTime = actyAddTime;
    }

    public String getActyMemberId() {
        return actyMemberId;
    }

    public void setActyMemberId(String actyMemberId) {
        this.actyMemberId = actyMemberId;
    }

    public String getActyIsHot() {
        return actyIsHot;
    }

    public void setActyIsHot(String actyIsHot) {
        this.actyIsHot = actyIsHot;
    }

    public String getActivityReserveNotice() {
        return activityReserveNotice;
    }

    public void setActivityReserveNotice(String activityReserveNotice) {
        this.activityReserveNotice = activityReserveNotice;
    }

    public String getActivityLitpic() {
        return activityLitpic;
    }

    public void setActivityLitpic(String activityLitpic) {
        this.activityLitpic = activityLitpic;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public String getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(String activityPrice) {
        this.activityPrice = activityPrice;
    }

    public String getActivityFromCity() {
        return activityFromCity;
    }

    public void setActivityFromCity(String activityFromCity) {
        this.activityFromCity = activityFromCity;
    }

    public String getActivityFromTime() {
        return activityFromTime;
    }

    public void setActivityFromTime(String activityFromTime) {
        this.activityFromTime = activityFromTime;
    }

    public String getActivityTripDays() {
        return activityTripDays;
    }

    public void setActivityTripDays(String activityTripDays) {
        this.activityTripDays = activityTripDays;
    }

    public String getActivityGroupAccount() {
        return activityGroupAccount;
    }

    public void setActivityGroupAccount(String activityGroupAccount) {
        this.activityGroupAccount = activityGroupAccount;
    }

    public String getActivityLineDetails() {
        return activityLineDetails;
    }

    public void setActivityLineDetails(String activityLineDetails) {
        this.activityLineDetails = activityLineDetails;
    }

    public String getActivityDetailDescribe() {
        return activityDetailDescribe;
    }

    public void setActivityDetailDescribe(String activityDetailDescribe) {
        this.activityDetailDescribe = activityDetailDescribe;
    }

    public String getActivityPriceDescribe() {
        return activityPriceDescribe;
    }

    public void setActivityPriceDescribe(String activityPriceDescribe) {
        this.activityPriceDescribe = activityPriceDescribe;
    }

    public String getActivityTripDescribe() {
        return activityTripDescribe;
    }

    public void setActivityTripDescribe(String activityTripDescribe) {
        this.activityTripDescribe = activityTripDescribe;
    }
}
