package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2017/1/5.
 */
public class HotelOrderCancelResponse {
    private String ErrorCode;
    private String ErrorMsg;

    public HotelOrderCancelResponse() {
    }

    public HotelOrderCancelResponse(String errorCode, String errorMsg) {
        ErrorCode = errorCode;
        ErrorMsg = errorMsg;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        ErrorMsg = errorMsg;
    }
}
