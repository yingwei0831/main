package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/11/15.
 * 签证国家
 */
public class VisaCountryInfo {

//    "国家二字码","国家中文名称","国家英文简称","国旗Url","洲代码","洲名称","是否热点(Y/N)"
    public String countryCode;
    public String countryName;
    public String countryNameEn;
    public String countryFlagUrl;
    public String continentCode;
    public String continentName;
    public boolean isHot;

    public VisaCountryInfo() {
    }

    public VisaCountryInfo(String countryCode, String countryName, String countryNameEn, String countryFlagUrl, String continentCode, String continentName, boolean isHot) {
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.countryNameEn = countryNameEn;
        this.countryFlagUrl = countryFlagUrl;
        this.continentCode = continentCode;
        this.continentName = continentName;
        this.isHot = isHot;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryNameEn() {
        return countryNameEn;
    }

    public void setCountryNameEn(String countryNameEn) {
        this.countryNameEn = countryNameEn;
    }

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

}
