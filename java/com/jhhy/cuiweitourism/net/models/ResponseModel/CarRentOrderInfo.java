package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by birney1 on 16/10/8.
 */
public class CarRentOrderInfo {
    //{"ErrorCode":"0","ErrorMsg":[],"RtRule":"201","RtType":"0","RtPsgPhone":"13898698745",
    // "RtPsgId":"0","RtPsgName":"张松钠","RtLinkerPhone":"13898698745","RtStartCityCode":"1",
    // "RtStartCityName":"北京市","RtStartflat":"40.081115580237","RtStartflng":"116.58797959531",
    // "RtStartName":"首都机场","RtStartAddress":"首都机场1号航站楼","RtEndCityCode":"1","RtEndCityName":"北京市",
    // "RtEndtlat":"39.96956","RtEndtlng":"116.40029","RtEndName":"浙江大厦",
    // "RtEndAddress":"朝阳区北三环中路安贞西里三区26号","RtDepartureTime":"2016-09-16 12:00:00",
    // "RtRequireLevel":"100","RtAppTime":"2016-09-09 12:00:00","RtMapType":"soso","RtComboId":"0",
    // "RtSmsPolicy":"1","RtFltNo":[],"RtExtraInfo":[],"RtPriceCarCode":"100",
    // "RtPriceCarName":"舒适型:大众帕萨特、凯美瑞等","RtPrice":"98.00","RtStartPrice":"12.00",
    // "RtNormalUnitPrice":"2.90","RtCounterFee":"0.00","RtLineType":"JJ","OrderStatus":"NW",
    // "RtDriverName":[],"RtDriverNum":"0","RtDriverCaType":[],"RtDriverCard":[],"RtDriverOrderCount":"0",
    // "RtFinishTime":[],"RtBeginChargeTime":[],"RtPayTime":[],"RtPayPrice":"0","RtCancelReason":[],
    // "RtCancelTime":[],"PriceDetails":[],"OrderNo":"610082238402","PlatOrderNo":"7983800358064909683",
    // "OutOrderNo":"03374960050945","RtSumPrice":"0.00"}
    public String ErrorCode;
    public ArrayList<String> ErrorMsg      ;
    public String RtRule        ;
    public String RtType        ;
    public String RtPsgPhone    ;
    public String RtPsgId       ;
    public String RtPsgName     ;
    public String RtLinkerPhone ;
    public String RtStartflat;
    public String RtStartflng;
    public String RtStartName;
    public String RtEndCityCode;
    public String RtEndCityName;
    public String RtEndtlat;
    public String RtEndtlng;
    public String RtEndName;
    public String RtEndAddress;
    public String RtDepartureTime;
    public String RtStartCityName;
    public String RtAppTime;
    public String RtMapType;
    public String RtComboId;
    public ArrayList<String> RtFltNo;
    public String RtPrice;
    public String OrderNo;
    public ArrayList<String> RtPayTime;
    public String RtSmsPolicy;
    public ArrayList<String> RtExtraInfo;
    public String RtPriceCarCode;
    public String RtPriceCarName;
    public String RtStartPrice;
    public String RtCounterFee;
    public String RtPayPrice;
    public String RtLineType;
    public String PlatOrderNo;
    public String OrderStatus;
    public String RtDriverNum;
    public ArrayList<String> RtFinishTime;
    public String RtRequireLevel;
    public String RtStartAddress;
    public ArrayList<String> RtCancelReason;
    public String RtStartCityCode   ;
    public String RtDriverOrderCount;
    public String RtNormalUnitPrice;
    public ArrayList<String> RtBeginChargeTime;
    public ArrayList<String> RtCancelTime;
    public ArrayList<String> PriceDetails;
    public String OutOrderNo;
    public String RtSumPrice;

    public ArrayList<String> RtDriverCard;

    public ArrayList<String> RtDriverCaType;
    public ArrayList<String> RtDriverName;

    public CarRentOrderInfo(String errorCode, ArrayList<String> errorMsg, String rtRule, String rtType, String rtPsgPhone, String rtPsgId, String rtPsgName, String rtLinkerPhone, String rtStartflat, String rtStartflng, String rtStartName, String rtEndCityCode, String rtEndCityName, String rtEndtlat, String rtEndtlng, String rtEndName, String rtEndAddress, String rtDepartureTime, String rtStartCityName, String rtAppTime, String rtMapType, String rtComboId, ArrayList<String> rtFltNo, String rtPrice, String orderNo, ArrayList<String> rtPayTime, String rtSmsPolicy, ArrayList<String> rtExtraInfo, String rtPriceCarCode, String rtPriceCarName, String rtStartPrice, String rtCounterFee, String rtPayPrice, String rtLineType, String platOrderNo, String orderStatus, String rtDriverNum, ArrayList<String> rtFinishTime, String rtRequireLevel, String rtStartAddress, ArrayList<String> rtCancelReason, String rtStartCityCode, String rtDriverOrderCount, String rtNormalUnitPrice, ArrayList<String> rtBeginChargeTime, ArrayList<String> rtCancelTime, ArrayList<String> priceDetails, String outOrderNo, String rtSumPrice, ArrayList<String> rtDriverCard, ArrayList<String> rtDriverCaType, ArrayList<String> rtDriverName) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
        RtRule = rtRule;
        RtType = rtType;
        RtPsgPhone = rtPsgPhone;
        RtPsgId = rtPsgId;
        RtPsgName = rtPsgName;
        RtLinkerPhone = rtLinkerPhone;
        RtStartflat = rtStartflat;
        RtStartflng = rtStartflng;
        RtStartName = rtStartName;
        RtEndCityCode = rtEndCityCode;
        RtEndCityName = rtEndCityName;
        RtEndtlat = rtEndtlat;
        RtEndtlng = rtEndtlng;
        RtEndName = rtEndName;
        RtEndAddress = rtEndAddress;
        RtDepartureTime = rtDepartureTime;
        RtStartCityName = rtStartCityName;
        RtAppTime = rtAppTime;
        RtMapType = rtMapType;
        RtComboId = rtComboId;
        RtFltNo = rtFltNo;
        RtPrice = rtPrice;
        OrderNo = orderNo;
        RtPayTime = rtPayTime;
        RtSmsPolicy = rtSmsPolicy;
        RtExtraInfo = rtExtraInfo;
        RtPriceCarCode = rtPriceCarCode;
        RtPriceCarName = rtPriceCarName;
        RtStartPrice = rtStartPrice;
        RtCounterFee = rtCounterFee;
        RtPayPrice = rtPayPrice;
        RtLineType = rtLineType;
        PlatOrderNo = platOrderNo;
        OrderStatus = orderStatus;
        RtDriverNum = rtDriverNum;
        RtFinishTime = rtFinishTime;
        RtRequireLevel = rtRequireLevel;
        RtStartAddress = rtStartAddress;
        RtCancelReason = rtCancelReason;
        RtStartCityCode = rtStartCityCode;
        RtDriverOrderCount = rtDriverOrderCount;
        RtNormalUnitPrice = rtNormalUnitPrice;
        RtBeginChargeTime = rtBeginChargeTime;
        RtCancelTime = rtCancelTime;
        PriceDetails = priceDetails;
        OutOrderNo = outOrderNo;
        RtSumPrice = rtSumPrice;
        RtDriverCard = rtDriverCard;
        RtDriverCaType = rtDriverCaType;
        RtDriverName = rtDriverName;
    }

    public CarRentOrderInfo() {
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public ArrayList<String> getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(ArrayList<String> errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getRtRule() {
        return RtRule;
    }

    public void setRtRule(String rtRule) {
        RtRule = rtRule;
    }

    public String getRtType() {
        return RtType;
    }

    public void setRtType(String rtType) {
        RtType = rtType;
    }

    public String getRtPsgPhone() {
        return RtPsgPhone;
    }

    public void setRtPsgPhone(String rtPsgPhone) {
        RtPsgPhone = rtPsgPhone;
    }

    public String getRtPsgId() {
        return RtPsgId;
    }

    public void setRtPsgId(String rtPsgId) {
        RtPsgId = rtPsgId;
    }

    public String getRtPsgName() {
        return RtPsgName;
    }

    public void setRtPsgName(String rtPsgName) {
        RtPsgName = rtPsgName;
    }

    public String getRtLinkerPhone() {
        return RtLinkerPhone;
    }

    public void setRtLinkerPhone(String rtLinkerPhone) {
        RtLinkerPhone = rtLinkerPhone;
    }

    public String getRtStartflat() {
        return RtStartflat;
    }

    public void setRtStartflat(String rtStartflat) {
        RtStartflat = rtStartflat;
    }

    public String getRtStartflng() {
        return RtStartflng;
    }

    public void setRtStartflng(String rtStartflng) {
        RtStartflng = rtStartflng;
    }

    public String getRtStartName() {
        return RtStartName;
    }

    public void setRtStartName(String rtStartName) {
        RtStartName = rtStartName;
    }

    public String getRtEndCityCode() {
        return RtEndCityCode;
    }

    public void setRtEndCityCode(String rtEndCityCode) {
        RtEndCityCode = rtEndCityCode;
    }

    public String getRtEndCityName() {
        return RtEndCityName;
    }

    public void setRtEndCityName(String rtEndCityName) {
        RtEndCityName = rtEndCityName;
    }

    public String getRtEndtlat() {
        return RtEndtlat;
    }

    public void setRtEndtlat(String rtEndtlat) {
        RtEndtlat = rtEndtlat;
    }

    public String getRtEndtlng() {
        return RtEndtlng;
    }

    public void setRtEndtlng(String rtEndtlng) {
        RtEndtlng = rtEndtlng;
    }

    public String getRtEndName() {
        return RtEndName;
    }

    public void setRtEndName(String rtEndName) {
        RtEndName = rtEndName;
    }

    public String getRtEndAddress() {
        return RtEndAddress;
    }

    public void setRtEndAddress(String rtEndAddress) {
        RtEndAddress = rtEndAddress;
    }

    public String getRtDepartureTime() {
        return RtDepartureTime;
    }

    public void setRtDepartureTime(String rtDepartureTime) {
        RtDepartureTime = rtDepartureTime;
    }

    public String getRtStartCityName() {
        return RtStartCityName;
    }

    public void setRtStartCityName(String rtStartCityName) {
        RtStartCityName = rtStartCityName;
    }

    public String getRtAppTime() {
        return RtAppTime;
    }

    public void setRtAppTime(String rtAppTime) {
        RtAppTime = rtAppTime;
    }

    public String getRtMapType() {
        return RtMapType;
    }

    public void setRtMapType(String rtMapType) {
        RtMapType = rtMapType;
    }

    public String getRtComboId() {
        return RtComboId;
    }

    public void setRtComboId(String rtComboId) {
        RtComboId = rtComboId;
    }

    public ArrayList<String> getRtFltNo() {
        return RtFltNo;
    }

    public void setRtFltNo(ArrayList<String> rtFltNo) {
        RtFltNo = rtFltNo;
    }

    public String getRtPrice() {
        return RtPrice;
    }

    public void setRtPrice(String rtPrice) {
        RtPrice = rtPrice;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public ArrayList<String> getRtPayTime() {
        return RtPayTime;
    }

    public void setRtPayTime(ArrayList<String> rtPayTime) {
        RtPayTime = rtPayTime;
    }

    public String getRtSmsPolicy() {
        return RtSmsPolicy;
    }

    public void setRtSmsPolicy(String rtSmsPolicy) {
        RtSmsPolicy = rtSmsPolicy;
    }

    public ArrayList<String> getRtExtraInfo() {
        return RtExtraInfo;
    }

    public void setRtExtraInfo(ArrayList<String> rtExtraInfo) {
        RtExtraInfo = rtExtraInfo;
    }

    public String getRtPriceCarCode() {
        return RtPriceCarCode;
    }

    public void setRtPriceCarCode(String rtPriceCarCode) {
        RtPriceCarCode = rtPriceCarCode;
    }

    public String getRtPriceCarName() {
        return RtPriceCarName;
    }

    public void setRtPriceCarName(String rtPriceCarName) {
        RtPriceCarName = rtPriceCarName;
    }

    public String getRtStartPrice() {
        return RtStartPrice;
    }

    public void setRtStartPrice(String rtStartPrice) {
        RtStartPrice = rtStartPrice;
    }

    public String getRtCounterFee() {
        return RtCounterFee;
    }

    public void setRtCounterFee(String rtCounterFee) {
        RtCounterFee = rtCounterFee;
    }

    public String getRtPayPrice() {
        return RtPayPrice;
    }

    public void setRtPayPrice(String rtPayPrice) {
        RtPayPrice = rtPayPrice;
    }

    public String getRtLineType() {
        return RtLineType;
    }

    public void setRtLineType(String rtLineType) {
        RtLineType = rtLineType;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        PlatOrderNo = platOrderNo;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getRtDriverNum() {
        return RtDriverNum;
    }

    public void setRtDriverNum(String rtDriverNum) {
        RtDriverNum = rtDriverNum;
    }

    public ArrayList<String> getRtFinishTime() {
        return RtFinishTime;
    }

    public void setRtFinishTime(ArrayList<String> rtFinishTime) {
        RtFinishTime = rtFinishTime;
    }

    public String getRtRequireLevel() {
        return RtRequireLevel;
    }

    public void setRtRequireLevel(String rtRequireLevel) {
        RtRequireLevel = rtRequireLevel;
    }

    public String getRtStartAddress() {
        return RtStartAddress;
    }

    public void setRtStartAddress(String rtStartAddress) {
        RtStartAddress = rtStartAddress;
    }

    public ArrayList<String> getRtCancelReason() {
        return RtCancelReason;
    }

    public void setRtCancelReason(ArrayList<String> rtCancelReason) {
        RtCancelReason = rtCancelReason;
    }

    public String getRtStartCityCode() {
        return RtStartCityCode;
    }

    public void setRtStartCityCode(String rtStartCityCode) {
        RtStartCityCode = rtStartCityCode;
    }

    public String getRtDriverOrderCount() {
        return RtDriverOrderCount;
    }

    public void setRtDriverOrderCount(String rtDriverOrderCount) {
        RtDriverOrderCount = rtDriverOrderCount;
    }

    public String getRtNormalUnitPrice() {
        return RtNormalUnitPrice;
    }

    public void setRtNormalUnitPrice(String rtNormalUnitPrice) {
        RtNormalUnitPrice = rtNormalUnitPrice;
    }

    public ArrayList<String> getRtBeginChargeTime() {
        return RtBeginChargeTime;
    }

    public void setRtBeginChargeTime(ArrayList<String> rtBeginChargeTime) {
        RtBeginChargeTime = rtBeginChargeTime;
    }

    public ArrayList<String> getRtCancelTime() {
        return RtCancelTime;
    }

    public void setRtCancelTime(ArrayList<String> rtCancelTime) {
        RtCancelTime = rtCancelTime;
    }

    public ArrayList<String> getPriceDetails() {
        return PriceDetails;
    }

    public void setPriceDetails(ArrayList<String> priceDetails) {
        PriceDetails = priceDetails;
    }

    public String getOutOrderNo() {
        return OutOrderNo;
    }

    public void setOutOrderNo(String outOrderNo) {
        OutOrderNo = outOrderNo;
    }

    public String getRtSumPrice() {
        return RtSumPrice;
    }

    public void setRtSumPrice(String rtSumPrice) {
        RtSumPrice = rtSumPrice;
    }

    public ArrayList<String> getRtDriverCard() {
        return RtDriverCard;
    }

    public void setRtDriverCard(ArrayList<String> rtDriverCard) {
        RtDriverCard = rtDriverCard;
    }

    public ArrayList<String> getRtDriverCaType() {
        return RtDriverCaType;
    }

    public void setRtDriverCaType(ArrayList<String> rtDriverCaType) {
        RtDriverCaType = rtDriverCaType;
    }

    public ArrayList<String> getRtDriverName() {
        return RtDriverName;
    }

    public void setRtDriverName(ArrayList<String> rtDriverName) {
        RtDriverName = rtDriverName;
    }

    @Override
    public String toString() {
        return "CarRentOrderInfo{" +
                "ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMsg=" + ErrorMsg +
                ", RtRule='" + RtRule + '\'' +
                ", RtType='" + RtType + '\'' +
                ", RtPsgPhone='" + RtPsgPhone + '\'' +
                ", RtPsgId='" + RtPsgId + '\'' +
                ", RtPsgName='" + RtPsgName + '\'' +
                ", RtLinkerPhone='" + RtLinkerPhone + '\'' +
                ", RtStartflat='" + RtStartflat + '\'' +
                ", RtStartflng='" + RtStartflng + '\'' +
                ", RtStartName='" + RtStartName + '\'' +
                ", RtEndCityCode='" + RtEndCityCode + '\'' +
                ", RtEndCityName='" + RtEndCityName + '\'' +
                ", RtEndtlat='" + RtEndtlat + '\'' +
                ", RtEndtlng='" + RtEndtlng + '\'' +
                ", RtEndName='" + RtEndName + '\'' +
                ", RtEndAddress='" + RtEndAddress + '\'' +
                ", RtDepartureTime='" + RtDepartureTime + '\'' +
                ", RtStartCityName='" + RtStartCityName + '\'' +
                ", RtAppTime='" + RtAppTime + '\'' +
                ", RtMapType='" + RtMapType + '\'' +
                ", RtComboId='" + RtComboId + '\'' +
                ", RtFltNo=" + RtFltNo +
                ", RtPrice='" + RtPrice + '\'' +
                ", OrderNo='" + OrderNo + '\'' +
                ", RtPayTime=" + RtPayTime +
                ", RtSmsPolicy='" + RtSmsPolicy + '\'' +
                ", RtExtraInfo=" + RtExtraInfo +
                ", RtPriceCarCode='" + RtPriceCarCode + '\'' +
                ", RtPriceCarName='" + RtPriceCarName + '\'' +
                ", RtStartPrice='" + RtStartPrice + '\'' +
                ", RtCounterFee='" + RtCounterFee + '\'' +
                ", RtPayPrice='" + RtPayPrice + '\'' +
                ", RtLineType='" + RtLineType + '\'' +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", OrderStatus='" + OrderStatus + '\'' +
                ", RtDriverNum='" + RtDriverNum + '\'' +
                ", RtFinishTime=" + RtFinishTime +
                ", RtRequireLevel='" + RtRequireLevel + '\'' +
                ", RtStartAddress='" + RtStartAddress + '\'' +
                ", RtCancelReason=" + RtCancelReason +
                ", RtStartCityCode='" + RtStartCityCode + '\'' +
                ", RtDriverOrderCount='" + RtDriverOrderCount + '\'' +
                ", RtNormalUnitPrice='" + RtNormalUnitPrice + '\'' +
                ", RtBeginChargeTime=" + RtBeginChargeTime +
                ", RtCancelTime=" + RtCancelTime +
                ", PriceDetails=" + PriceDetails +
                ", OutOrderNo='" + OutOrderNo + '\'' +
                ", RtSumPrice='" + RtSumPrice + '\'' +
                ", RtDriverCard=" + RtDriverCard +
                ", RtDriverCaType=" + RtDriverCaType +
                ", RtDriverName=" + RtDriverName +
                '}';
    }
}
