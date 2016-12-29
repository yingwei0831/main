package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/12/29.
 * 酒店数据校验返回
 */
public class HotelPriceCheckResponse {

    /**
     * ErrorCode : 0
     * ErrorMsg : OK:
     * GuaranteeRate : 0.0
     * CurrencyCode : RMB
     * CancelTime : 2016-12-29T23:59:59+08:00
     */

    private String ErrorCode;
    private String ErrorMsg;
    private String GuaranteeRate;
    private String CurrencyCode;
    private String CancelTime;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getGuaranteeRate() {
        return GuaranteeRate;
    }

    public void setGuaranteeRate(String GuaranteeRate) {
        this.GuaranteeRate = GuaranteeRate;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public String getCancelTime() {
        return CancelTime;
    }

    public void setCancelTime(String CancelTime) {
        this.CancelTime = CancelTime;
    }
}
