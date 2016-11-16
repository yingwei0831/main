package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Created by jiahe008 on 2016/11/15.
 * 签证详情
 */
public class VisaDetailInfo implements Serializable{

//            "签证产品ID",
//            "签证产品名称",
//            "签证类型",

//            "洲编码",
//            "洲名称",
//            "国家编码",
//            "国家名称",
//            "国旗Url",

//            "送签地三字码",
//            "送签地",
//            "是否加急(Y/N)",      Y
//            "办签费用",
//            "使馆办理时间",

//            "是否需要面签",         1
//            "签证有效时间",
//            "可停留天数",
//            "入境次数",

//            "办签须知",
//            "受理范围",
//            "备注",
//            {
//                "客户类别": [
//                                [
//                                    "资料名称",
//                                    "是否必要材料",
//                                    "模版",
//                                    "备注"
//                                ]
//                ]
//            },
//     "办签底价",
//     "办签加价",


//    "在职人员": [
//                    [
//                        "因私护照（原件）",
//                        "申请表",    ?
//                        "Y",
//                        "",
//                        "数量:1；是否副本:否；护照完整无破损、无水渍有效期离出发日期应至少还有6个月至少有两页完整连续的空白页不包含备注页"
//                    ],
//                    [
//                        "照片（原件）",
//                        "申请表",    ?
//                        "Y",
//                        "",
//                        "数量:1；是否副本:否；近6个月内拍摄彩色照片2张请在照片反面写上名字规格：35mmX45mm长方形白色背景五官端正清晰眼睛睁开"
//                    ]
//                ]


    public String visaId;
    public String visaName;
    public String visaType;

    public String continentCode;
    public String continentName;
    public String countryCode;
    public String countryName;
    public String countryFlagUrl;

    public String visaAddressCode;
    public String visaAddress;
    public boolean visaHurry;
    public String visaPrice;
    public String visaTime; //时间

    public boolean needInterview;
    public String visaPeriodOfValidate;
    public String visaStayPeriod;
    public String innerTimes;

    public String notice;
    public String acceptArea; //受理范围
    public String remark;


    public  VisaMaterialCollection materialCollectionl;

    public VisaMaterialCollection getMaterialCollectionl()
    {
        if (materialCollectionl == null){
            materialCollectionl = new VisaMaterialCollection();
        }
        return materialCollectionl;
    }
//    public ArrayList<VisaClassification> classification; //签证人员类别
//    public LinkedList<VisaClassification> classification; //签证人员类别
//    public LinkedHashMap<String, VisaClassification> classification; //签证人员类别
    public String visaPriceLower;
    public String visaPriceAdditional;

    public VisaDetailInfo() {
    }

    public VisaDetailInfo(String visaId, String visaName, String visaType, String continentCode, String continentName,
                          String countryCode, String countryName, String countryFlagUrl, String visaAddressCode, String visaAddress,
                          boolean visaHurry, String visaPrice, String visaTime, boolean needInterview, String visaPeriodOfValidate,
                          String visaStayPeriod, String innerTimes, String notice, String acceptArea, String remark, String visaPriceLower,
                          String visaPriceAdditional, VisaMaterialCollection materialCollectionl) {
        this.visaId = visaId;
        this.visaName = visaName;
        this.visaType = visaType;
        this.continentCode = continentCode;
        this.continentName = continentName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.countryFlagUrl = countryFlagUrl;
        this.visaAddressCode = visaAddressCode;
        this.visaAddress = visaAddress;
        this.visaHurry = visaHurry;
        this.visaPrice = visaPrice;
        this.visaTime = visaTime;
        this.needInterview = needInterview;
        this.visaPeriodOfValidate = visaPeriodOfValidate;
        this.visaStayPeriod = visaStayPeriod;
        this.innerTimes = innerTimes;
        this.notice = notice;
        this.acceptArea = acceptArea;
        this.remark = remark;
        this.visaPriceLower = visaPriceLower;
        this.visaPriceAdditional = visaPriceAdditional;
        this.materialCollectionl = materialCollectionl;
    }

    public VisaMaterialCollection getClassification() {
        return materialCollectionl;
    }

    public void setClassification(VisaMaterialCollection classification) {
        this.materialCollectionl = classification;
    }

    public String getVisaId() {
        return visaId;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public String getVisaName() {
        return visaName;
    }

    public void setVisaName(String visaName) {
        this.visaName = visaName;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
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

    public String getCountryFlagUrl() {
        return countryFlagUrl;
    }

    public void setCountryFlagUrl(String countryFlagUrl) {
        this.countryFlagUrl = countryFlagUrl;
    }

    public String getVisaAddressCode() {
        return visaAddressCode;
    }

    public void setVisaAddressCode(String visaAddressCode) {
        this.visaAddressCode = visaAddressCode;
    }

    public String getVisaAddress() {
        return visaAddress;
    }

    public void setVisaAddress(String visaAddress) {
        this.visaAddress = visaAddress;
    }

    public boolean isVisaHurry() {
        return visaHurry;
    }

    public void setVisaHurry(boolean visaHurry) {
        this.visaHurry = visaHurry;
    }

    public String getVisaPrice() {
        return visaPrice;
    }

    public void setVisaPrice(String visaPrice) {
        this.visaPrice = visaPrice;
    }

    public String getVisaTime() {
        return visaTime;
    }

    public void setVisaTime(String visaTime) {
        this.visaTime = visaTime;
    }

    public boolean isNeedInterview() {
        return needInterview;
    }

    public void setNeedInterview(boolean needInterview) {
        this.needInterview = needInterview;
    }

    public String getVisaPeriodOfValidate() {
        return visaPeriodOfValidate;
    }

    public void setVisaPeriodOfValidate(String visaPeriodOfValidate) {
        this.visaPeriodOfValidate = visaPeriodOfValidate;
    }

    public String getVisaStayPeriod() {
        return visaStayPeriod;
    }

    public void setVisaStayPeriod(String visaStayPeriod) {
        this.visaStayPeriod = visaStayPeriod;
    }

    public String getInnerTimes() {
        return innerTimes;
    }

    public void setInnerTimes(String innerTimes) {
        this.innerTimes = innerTimes;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getAcceptArea() {
        return acceptArea;
    }

    public void setAcceptArea(String acceptArea) {
        this.acceptArea = acceptArea;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getVisaPriceLower() {
        return visaPriceLower;
    }

    public void setVisaPriceLower(String visaPriceLower) {
        this.visaPriceLower = visaPriceLower;
    }

    public String getVisaPriceAdditional() {
        return visaPriceAdditional;
    }

    public void setVisaPriceAdditional(String visaPriceAdditional) {
        this.visaPriceAdditional = visaPriceAdditional;
    }

    @Override
    public String toString() {
        return "VisaDetailInfo{" +
                "visaId='" + visaId + '\'' +
                ", visaName='" + visaName + '\'' +
                ", visaType='" + visaType + '\'' +
                ", continentCode='" + continentCode + '\'' +
                ", continentName='" + continentName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryFlagUrl='" + countryFlagUrl + '\'' +
                ", visaAddressCode='" + visaAddressCode + '\'' +
                ", visaAddress='" + visaAddress + '\'' +
                ", visaHurry=" + visaHurry +
                ", visaPrice='" + visaPrice + '\'' +
                ", visaTime='" + visaTime + '\'' +
                ", needInterview=" + needInterview +
                ", visaPeriodOfValidate='" + visaPeriodOfValidate + '\'' +
                ", visaStayPeriod='" + visaStayPeriod + '\'' +
                ", innerTimes='" + innerTimes + '\'' +
                ", notice='" + notice + '\'' +
                ", acceptArea='" + acceptArea + '\'' +
                ", remark='" + remark + '\'' +
                ", materialCollectionl=" + materialCollectionl +
                ", visaPriceLower='" + visaPriceLower + '\'' +
                ", visaPriceAdditional='" + visaPriceAdditional + '\'' +
                '}';
    }
}
