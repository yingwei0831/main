package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/11/15.
 * 签证国家
 */
public class VisaHotInfo implements Comparable<VisaHotInfo>{

//    "996",                                             "签证销售产品ID",
//    "澳大利亚旅游签证加急",                "签证销售产品名称",
//    "http://manage.yeegoyun.com:8102/Images/Country/A/AU.png",  "国旗Url",

//    "BJS",            "送签地三字码",
//    "北京",            "送签地",
//    "3",            "办理时间",
//    "Y",            "是否加急",
//    "1年",            "有效期",

//    "90天",            "停留时间",
//    "4",            "入境次数",
//    "1",            "是否需要面签",

//    "6000",            "办签费用",
//    "5200",            "办签底价",
//    "800",            "办签加价",

//    "4",            "签证类型ID",
//    "旅游签证",         "签证类型",
//    "BC",            "产品来源(NA/BC)",
//    "2",            "签证洲编码",
//    "大洋洲",          "签证洲名称",
//    "AU",            "签证国家代码",
//    "澳大利亚"          "签证国家名称"

    public String visaId;
    public String visaName;
    public String visaFlagUrl;

    public String visaAddressCode;
    public String visaAddress;
    public String visaTime;
    public boolean visaHurry;
    public String visaPeriodOfValidaty;

    public String visaStayPeriod;
    public String innerTimes;
    public boolean needInterview;

    public String visaPrice;
    public String visaPriceLower;
    public String visaPriceAdditional;

    public String visaTypeId;
    public String visaType;
    public String productResource;
    public String visaCCode;
    public String visaCName;
    public String visaNationCode;
    public String visaNationName;

    public String getVisaId() {
        return visaId;
    }

    public String getVisaName() {
        return visaName;
    }

    public String getVisaFlagUrl() {
        return visaFlagUrl;
    }

    public String getVisaAddressCode() {
        return visaAddressCode;
    }

    public String getVisaAddress() {
        return visaAddress;
    }

    public String getVisaTime() {
        return visaTime;
    }

    public boolean isVisaHurry() {
        return visaHurry;
    }

    public String getVisaPeriodOfValidaty() {
        return visaPeriodOfValidaty;
    }

    public String getVisaStayPeriod() {
        return visaStayPeriod;
    }

    public String getInnerTimes() {
        return innerTimes;
    }

    public boolean isNeedInterview() {
        return needInterview;
    }

    public String getVisaPrice() {
        return visaPrice;
    }

    public String getVisaPriceLower() {
        return visaPriceLower;
    }

    public String getVisaPriceAdditional() {
        return visaPriceAdditional;
    }

    public String getVisaTypeId() {
        return visaTypeId;
    }

    public String getVisaType() {
        return visaType;
    }

    public String getProductResource() {
        return productResource;
    }

    public String getVisaCCode() {
        return visaCCode;
    }

    public String getVisaCName() {
        return visaCName;
    }

    public String getVisaNationCode() {
        return visaNationCode;
    }

    public String getVisaNationName() {
        return visaNationName;
    }

    public void setVisaId(String visaId) {
        this.visaId = visaId;
    }

    public void setVisaName(String visaName) {
        this.visaName = visaName;
    }

    public void setVisaFlagUrl(String visaFlagUrl) {
        this.visaFlagUrl = visaFlagUrl;
    }

    public void setVisaAddressCode(String visaAddressCode) {
        this.visaAddressCode = visaAddressCode;
    }

    public void setVisaAddress(String visaAddress) {
        this.visaAddress = visaAddress;
    }

    public void setVisaTime(String visaTime) {
        this.visaTime = visaTime;
    }

    public void setVisaHurry(boolean visaHurry) {
        this.visaHurry = visaHurry;
    }

    public void setVisaPeriodOfValidaty(String visaPeriodOfValidaty) {
        this.visaPeriodOfValidaty = visaPeriodOfValidaty;
    }

    public void setVisaStayPeriod(String visaStayPeriod) {
        this.visaStayPeriod = visaStayPeriod;
    }

    public void setInnerTimes(String innerTimes) {
        this.innerTimes = innerTimes;
    }

    public void setNeedInterview(boolean needInterview) {
        this.needInterview = needInterview;
    }

    public void setVisaPrice(String visaPrice) {
        this.visaPrice = visaPrice;
    }

    public void setVisaPriceLower(String visaPriceLower) {
        this.visaPriceLower = visaPriceLower;
    }

    public void setVisaPriceAdditional(String visaPriceAdditional) {
        this.visaPriceAdditional = visaPriceAdditional;
    }

    public void setVisaTypeId(String visaTypeId) {
        this.visaTypeId = visaTypeId;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public void setProductResource(String productResource) {
        this.productResource = productResource;
    }

    public void setVisaCCode(String visaCCode) {
        this.visaCCode = visaCCode;
    }

    public void setVisaCName(String visaCName) {
        this.visaCName = visaCName;
    }

    public void setVisaNationCode(String visaNationCode) {
        this.visaNationCode = visaNationCode;
    }

    public void setVisaNationName(String visaNationName) {
        this.visaNationName = visaNationName;
    }

    public VisaHotInfo() {
    }

    public VisaHotInfo(String visaId, String visaName, String visaFlagUrl, String visaAddressCode, String visaAddress, String visaTime, boolean visaHurry, String visaPeriodOfValidaty, String visaStayPeriod, String innerTimes, boolean needInterview, String visaPrice, String visaPriceLower, String visaPriceAdditional, String visaTypeId, String visaType, String productResource, String visaCCode, String visaCName, String visaNationCode, String visaNationName) {
        this.visaId = visaId;
        this.visaName = visaName;
        this.visaFlagUrl = visaFlagUrl;
        this.visaAddressCode = visaAddressCode;
        this.visaAddress = visaAddress;
        this.visaTime = visaTime;
        this.visaHurry = visaHurry;
        this.visaPeriodOfValidaty = visaPeriodOfValidaty;
        this.visaStayPeriod = visaStayPeriod;
        this.innerTimes = innerTimes;
        this.needInterview = needInterview;
        this.visaPrice = visaPrice;
        this.visaPriceLower = visaPriceLower;
        this.visaPriceAdditional = visaPriceAdditional;
        this.visaTypeId = visaTypeId;
        this.visaType = visaType;
        this.productResource = productResource;
        this.visaCCode = visaCCode;
        this.visaCName = visaCName;
        this.visaNationCode = visaNationCode;
        this.visaNationName = visaNationName;
    }

    @Override
    public int compareTo(VisaHotInfo visaHotInfo) {
        if (Integer.parseInt(this.visaPrice) > Integer.parseInt(visaHotInfo.visaPrice)){
            return 1;
        }else if (Integer.parseInt(this.visaPrice) < Integer.parseInt(visaHotInfo.visaPrice)){
            return -1;
        }
        return 0;
    }
}
