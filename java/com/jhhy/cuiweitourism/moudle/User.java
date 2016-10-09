package com.jhhy.cuiweitourism.moudle;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/3/28.
 */
public class User implements Serializable{

    private int _id;//此处为本地数据库自动生成id
    private String userId;//此处为服务器返回的id
    private String userPhoneNumber;
    private String userPassword;

    private String userNickName;
    private String userGender;
    private String userIconPath;

    private String userTrueName; //真实名字
    private String userCardId; //身份证号
    private String userScore; //积分

//    private String userDistributionNum;
//    private String userGrabNum;
//    private boolean isAngel;
    private String userAddress;//北京市朝阳区安贞门26号
    private String district;//朝阳区
    private String street;//北三环中路

    private String userCity;//北京市
    private String userCityCode;//北京市areaid

    private String longitude;//经度
    private String latitude;//纬度


    public User() {
    }

    public String getUserTrueName() {
        return userTrueName;
    }

    public void setUserTrueName(String userTrueName) {
        this.userTrueName = userTrueName;
    }

    public String getUserCardId() {
        return userCardId;
    }

    public void setUserCardId(String userCardId) {
        this.userCardId = userCardId;
    }

    public String getUserScore() {
        return userScore;
    }

    public void setUserScore(String userScore) {
        this.userScore = userScore;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserCityCode() {
        return userCityCode;
    }

    public void setUserCityCode(String userCityCode) {
        this.userCityCode = userCityCode;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

//    public boolean isAngel() {
//        return isAngel;
//    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    @Override
    public String toString() {
        return "User{" +
                "_id=" + _id +
                ", userId='" + userId + '\'' +
                ", userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userGender='" + userGender + '\'' +
                ", userIconPath='" + userIconPath + '\'' +
//                ", userDistributionNum='" + userDistributionNum + '\'' +
//                ", userGrabNum='" + userGrabNum + '\'' +
//                ", isAngel=" + isAngel +
                ", userAddress='" + userAddress + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", userCity='" + userCity + '\'' +
                ", userCityCode='" + userCityCode + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                '}';
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserIconPath() {
        return userIconPath;
    }

    public void setUserIconPath(String userIconPath) {
        this.userIconPath = userIconPath;
    }

//    public String getUserDistributionNum() {
//        return userDistributionNum;
//    }

//    public void setUserDistributionNum(String userDistributionNum) {
//        this.userDistributionNum = userDistributionNum;
//    }

//    public String getUserGrabNum() {
//        return userGrabNum;
//    }

//    public void setUserGrabNum(String userGrabNum) {
//        this.userGrabNum = userGrabNum;
//    }

//    public boolean getIsAngel() {
//        return isAngel;
//    }

//    public void setAngel(boolean angel) {
//        isAngel = angel;
//    }
}
