package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/10.
 * 酒店下单到第三方平台
 */
public class HotelOrderToPlatformResponse {

    /**
     * ErrorCode : 0
     * OrderNo : 7011017150004325
     * PlatOrderNo : 197426056
     * CancelTime : 1970-01-01T00:00:00+08:00
     * GuaranteeAmount : 0.0
     * CurrencyCode : RMB
     * OrderStatus : 10
     * IsInstantConfirm : false
     * PaymentDeadlineTime : 0001-01-01T00:00:00
     * GuranteeRule : []
     * PrepayRule : {"PrepayRuleId":"86483547","Description":"预付规则:您入住期间需要提供信用卡预付全额房费,此单一经预订成功将无法变更取消,如未入住，将扣除全额房费.","DateType":"CheckInDay","StartDate":"2015-11-20","EndDate":"2020-11-26","WeekSet":[],"ChangeRule":"PrepayNoChange","DateNum":"1970-01-01","Time":"18:00","DeductFeesBefore":"0","DeductNumBefore":"0.0","CashScaleFirstAfter":"FristNight","DeductFeesAfter":"0","DeductNumAfter":"0.0","CashScaleFirstBefore":"FristNight","Hour":"24","Hour2":"0"}
     */

    private String ErrorCode;
    private String OrderNo;
    private String PlatOrderNo;
    private String CancelTime;
    private String GuaranteeAmount;
    private String CurrencyCode;
    private String OrderStatus;
    private String IsInstantConfirm;
    private String PaymentDeadlineTime;
    private PrepayRuleBean PrepayRule;
    private List<?> GuranteeRule;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
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

    public void setIsInstantConfirm(String IsInstantConfirm) {
        this.IsInstantConfirm = IsInstantConfirm;
    }

    public String getPaymentDeadlineTime() {
        return PaymentDeadlineTime;
    }

    public void setPaymentDeadlineTime(String PaymentDeadlineTime) {
        this.PaymentDeadlineTime = PaymentDeadlineTime;
    }

    public PrepayRuleBean getPrepayRule() {
        return PrepayRule;
    }

    public void setPrepayRule(PrepayRuleBean PrepayRule) {
        this.PrepayRule = PrepayRule;
    }

    public List<?> getGuranteeRule() {
        return GuranteeRule;
    }

    public void setGuranteeRule(List<?> GuranteeRule) {
        this.GuranteeRule = GuranteeRule;
    }

    public static class PrepayRuleBean {
        /**
         * PrepayRuleId : 86483547
         * Description : 预付规则:您入住期间需要提供信用卡预付全额房费,此单一经预订成功将无法变更取消,如未入住，将扣除全额房费.
         * DateType : CheckInDay
         * StartDate : 2015-11-20
         * EndDate : 2020-11-26
         * WeekSet : []
         * ChangeRule : PrepayNoChange
         * DateNum : 1970-01-01
         * Time : 18:00
         * DeductFeesBefore : 0
         * DeductNumBefore : 0.0
         * CashScaleFirstAfter : FristNight
         * DeductFeesAfter : 0
         * DeductNumAfter : 0.0
         * CashScaleFirstBefore : FristNight
         * Hour : 24
         * Hour2 : 0
         */

        private String PrepayRuleId;
        private String Description;
        private String DateType;
        private String StartDate;
        private String EndDate;
        private String ChangeRule;
        private String DateNum;
        private String Time;
        private String DeductFeesBefore;
        private String DeductNumBefore;
        private String CashScaleFirstAfter;
        private String DeductFeesAfter;
        private String DeductNumAfter;
        private String CashScaleFirstBefore;
        private String Hour;
        private String Hour2;
        private List<?> WeekSet;

        public String getPrepayRuleId() {
            return PrepayRuleId;
        }

        public void setPrepayRuleId(String PrepayRuleId) {
            this.PrepayRuleId = PrepayRuleId;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getDateType() {
            return DateType;
        }

        public void setDateType(String DateType) {
            this.DateType = DateType;
        }

        public String getStartDate() {
            return StartDate;
        }

        public void setStartDate(String StartDate) {
            this.StartDate = StartDate;
        }

        public String getEndDate() {
            return EndDate;
        }

        public void setEndDate(String EndDate) {
            this.EndDate = EndDate;
        }

        public String getChangeRule() {
            return ChangeRule;
        }

        public void setChangeRule(String ChangeRule) {
            this.ChangeRule = ChangeRule;
        }

        public String getDateNum() {
            return DateNum;
        }

        public void setDateNum(String DateNum) {
            this.DateNum = DateNum;
        }

        public String getTime() {
            return Time;
        }

        public void setTime(String Time) {
            this.Time = Time;
        }

        public String getDeductFeesBefore() {
            return DeductFeesBefore;
        }

        public void setDeductFeesBefore(String DeductFeesBefore) {
            this.DeductFeesBefore = DeductFeesBefore;
        }

        public String getDeductNumBefore() {
            return DeductNumBefore;
        }

        public void setDeductNumBefore(String DeductNumBefore) {
            this.DeductNumBefore = DeductNumBefore;
        }

        public String getCashScaleFirstAfter() {
            return CashScaleFirstAfter;
        }

        public void setCashScaleFirstAfter(String CashScaleFirstAfter) {
            this.CashScaleFirstAfter = CashScaleFirstAfter;
        }

        public String getDeductFeesAfter() {
            return DeductFeesAfter;
        }

        public void setDeductFeesAfter(String DeductFeesAfter) {
            this.DeductFeesAfter = DeductFeesAfter;
        }

        public String getDeductNumAfter() {
            return DeductNumAfter;
        }

        public void setDeductNumAfter(String DeductNumAfter) {
            this.DeductNumAfter = DeductNumAfter;
        }

        public String getCashScaleFirstBefore() {
            return CashScaleFirstBefore;
        }

        public void setCashScaleFirstBefore(String CashScaleFirstBefore) {
            this.CashScaleFirstBefore = CashScaleFirstBefore;
        }

        public String getHour() {
            return Hour;
        }

        public void setHour(String Hour) {
            this.Hour = Hour;
        }

        public String getHour2() {
            return Hour2;
        }

        public void setHour2(String Hour2) {
            this.Hour2 = Hour2;
        }

        public List<?> getWeekSet() {
            return WeekSet;
        }

        public void setWeekSet(List<?> WeekSet) {
            this.WeekSet = WeekSet;
        }
    }
}
