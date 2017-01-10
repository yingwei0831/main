package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2017/1/3.
 */
public class HotelOrderResponse implements Serializable{
//    酒店取消订单
//    <ErrorCode>0</ErrorCode>
//    <!--0 成功，否则均失败-->
//    <ErrorMsg>错误</ErrorMsg>
//    <!--取消失败的失败原因-->

//    酒店下订单 modify
//{"head":{"res_code":"0000","res_msg":"success","res_arg":"下单成功"},"body":{"order":"02202946312540","price":"5268.00"}}

//    {"head":{"res_code":"0000","res_msg":"success","res_arg":"获取成功"},
//     "body":{
//          "ErrorCode":"0",
//          "OrderNo":"701031544401",
//          "PlatOrderNo":"196101324",
//          "CancelTime":"2017-01-04T00:00:00+08:00",
//          "GuaranteeAmount":"0.0",
//          "CurrencyCode":"RMB",
//          "OrderStatus":"10",
//          "IsInstantConfirm":"false",
//          "PaymentDeadlineTime":"0001-01-01T00:00:00"}
//    }
    /**
     * ErrorCode : 0                    0 成功否则失败
     * ErrorMsg :                       失败原因
     * OrderNo : 511161652001           易购单号
     * PlatOrderNo : 161615994          供应商单号
     * CancelTime : 2016-01-31T00:00:00+08:00       最晚取消时间
     *                      如果日期为 9999-12-30 23:00:00 等条件代表不限制取消时间，不限制取消时间的订单，最晚取消时间为最早到店时间之前可以取消
     * GuaranteeAmount : 0.0            可为空，担保金额
     * CurrencyCode : RMB               可为空，货币类型
     *                      说明：如果此订单是担保订单，则在此列出担保金额，币种是人民币(如果提交订单时候的是港币，这里也会被换算成对应金额 的人民币)。
     * OrderStatus : 12                 10 未确认，11 已取消，12 已确认，13 入住中，14 正常离店，15 提前离店，16 NoShow
     */

    private String ErrorCode;
    private String ErrorMsg;

    private String order;
    private String price;

    private String OrderNo;
    private String PlatOrderNo;
    private String CancelTime;
    private String GuaranteeAmount;
    private String CurrencyCode;
    private String OrderStatus;
    private String IsInstantConfirm;
    private String PaymentDeadlineTime;

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

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String PlatOrderNo) {
        this.PlatOrderNo = PlatOrderNo;
    }

    public String getCancelTime() {
        return CancelTime;
    }

    public void setCancelTime(String CancelTime) {
        this.CancelTime = CancelTime;
    }

    public String getGuaranteeAmount() {
        return GuaranteeAmount;
    }

    public void setGuaranteeAmount(String GuaranteeAmount) {
        this.GuaranteeAmount = GuaranteeAmount;
    }

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String OrderStatus) {
        this.OrderStatus = OrderStatus;
    }

    public String getIsInstantConfirm() {
        return IsInstantConfirm;
    }

    public void setIsInstantConfirm(String isInstantConfirm) {
        IsInstantConfirm = isInstantConfirm;
    }

    public String getPaymentDeadlineTime() {
        return PaymentDeadlineTime;
    }

    public void setPaymentDeadlineTime(String paymentDeadlineTime) {
        PaymentDeadlineTime = paymentDeadlineTime;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "HotelOrderResponse{" +
                "ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", order='" + order + '\'' +
                ", price='" + price + '\'' +
                ", OrderNo='" + OrderNo + '\'' +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", CancelTime='" + CancelTime + '\'' +
                ", GuaranteeAmount='" + GuaranteeAmount + '\'' +
                ", CurrencyCode='" + CurrencyCode + '\'' +
                ", OrderStatus='" + OrderStatus + '\'' +
                ", IsInstantConfirm='" + IsInstantConfirm + '\'' +
                ", PaymentDeadlineTime='" + PaymentDeadlineTime + '\'' +
                '}';
    }
}
