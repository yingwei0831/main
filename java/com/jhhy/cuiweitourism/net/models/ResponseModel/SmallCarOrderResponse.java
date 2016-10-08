package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by birney1 on 16/10/2.
 */
public class SmallCarOrderResponse {

    //"ErrorCode":"0","ErrorMsg":[],"PlatOrderNo":"7947755921794941865"
    // ,"OrderNo":"610021726402","EstimatePrice":"98.4"

    public String ErrorCode;
    public ArrayList ErrorMsg;
    public String  PlatOrderNo;
    public String OrderNo;
    public String EstimatePrice;

    public SmallCarOrderResponse(String errorCode, ArrayList errorMsg, String platOrderNo, String orderNo, String estimatePrice) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
        PlatOrderNo = platOrderNo;
        OrderNo = orderNo;
        EstimatePrice = estimatePrice;
    }

    public SmallCarOrderResponse() {
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public ArrayList getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(ArrayList errorMsg) {
        ErrorMsg = errorMsg;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String platOrderNo) {
        PlatOrderNo = platOrderNo;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String orderNo) {
        OrderNo = orderNo;
    }

    public String getEstimatePrice() {
        return EstimatePrice;
    }

    public void setEstimatePrice(String estimatePrice) {
        EstimatePrice = estimatePrice;
    }

    @Override
    public String toString() {
        return "SmallCarOrderResponse{" +
                "ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMsg=" + ErrorMsg +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", OrderNo='" + OrderNo + '\'' +
                ", EstimatePrice='" + EstimatePrice + '\'' +
                '}';
    }
}
