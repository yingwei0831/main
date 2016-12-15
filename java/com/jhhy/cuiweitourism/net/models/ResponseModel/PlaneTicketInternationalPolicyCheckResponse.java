package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/13.
 * 国际机票验价返回信息
 */
public class PlaneTicketInternationalPolicyCheckResponse {

    /**
     * RequestID : OpenAPI92828832
     * ErrorCode : 0
     * ErrorMsg : []
     * ErrorStack : []
     * SearchInfo : {"TotalTime":"4.521","TotalPlatforms":"1","SuccessPlatforms":"0","PolicyCount":"0"}
     * Policys : {"Policy":{"Flag":"N","PlatCode":"017","ErrMsg":"未获取到相关政策信息！获取到的政策被过滤掉了！","IsVip":"0","AccountLevel":"0","TaxAmount":"0","Price":"0","SettlePrice":"0","AduTaxFare":"0","AduRewardTicketFare":"0","AduRewardDiscount":"0","AduEtdzFare":"0","AduRewardMoney":"0","ChdBaseDiscount":"0","ChdRewardDiscount":"0","ChdTaxFare":"0","ChdRewardTicketFare":"0","ChdEtdzFare":"0","ChdRewardMoney":"0"}}
     */

    private String RequestID;
    private String ErrorCode;
    private SearchInfoBean SearchInfo;
    private PolicysBean Policys;
    private List<String> ErrorMsg;
    private List<String> ErrorStack;

    public String getRequestID() {
        return RequestID;
    }

    public void setRequestID(String RequestID) {
        this.RequestID = RequestID;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public SearchInfoBean getSearchInfo() {
        return SearchInfo;
    }

    public void setSearchInfo(SearchInfoBean SearchInfo) {
        this.SearchInfo = SearchInfo;
    }

    public PolicysBean getPolicys() {
        return Policys;
    }

    public void setPolicys(PolicysBean Policys) {
        this.Policys = Policys;
    }

    public List<?> getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(List<String> ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public List<String> getErrorStack() {
        return ErrorStack;
    }

    public void setErrorStack(List<String> ErrorStack) {
        this.ErrorStack = ErrorStack;
    }

    public static class SearchInfoBean {
        /**
         * TotalTime : 4.521
         * TotalPlatforms : 1
         * SuccessPlatforms : 0
         * PolicyCount : 0
         */

        private String TotalTime;
        private String TotalPlatforms;
        private String SuccessPlatforms;
        private String PolicyCount;

        public String getTotalTime() {
            return TotalTime;
        }

        public void setTotalTime(String TotalTime) {
            this.TotalTime = TotalTime;
        }

        public String getTotalPlatforms() {
            return TotalPlatforms;
        }

        public void setTotalPlatforms(String TotalPlatforms) {
            this.TotalPlatforms = TotalPlatforms;
        }

        public String getSuccessPlatforms() {
            return SuccessPlatforms;
        }

        public void setSuccessPlatforms(String SuccessPlatforms) {
            this.SuccessPlatforms = SuccessPlatforms;
        }

        public String getPolicyCount() {
            return PolicyCount;
        }

        public void setPolicyCount(String PolicyCount) {
            this.PolicyCount = PolicyCount;
        }
    }

    public static class PolicysBean {
        /**
         * Policy : {"Flag":"N","PlatCode":"017","ErrMsg":"未获取到相关政策信息！获取到的政策被过滤掉了！","IsVip":"0","AccountLevel":"0","TaxAmount":"0","Price":"0","SettlePrice":"0","AduTaxFare":"0","AduRewardTicketFare":"0","AduRewardDiscount":"0","AduEtdzFare":"0","AduRewardMoney":"0","ChdBaseDiscount":"0","ChdRewardDiscount":"0","ChdTaxFare":"0","ChdRewardTicketFare":"0","ChdEtdzFare":"0","ChdRewardMoney":"0"}
         */

        private PolicyBean Policy;

        public PolicyBean getPolicy() {
            return Policy;
        }

        public void setPolicy(PolicyBean Policy) {
            this.Policy = Policy;
        }
    }
    public static class PolicyBean {
        /**
         * Flag : N
         * PlatCode : 017
         * ErrMsg : 未获取到相关政策信息！获取到的政策被过滤掉了！
         * IsVip : 0
         * AccountLevel : 0
         * TaxAmount : 0
         * Price : 0
         * SettlePrice : 0
         * AduTaxFare : 0
         * AduRewardTicketFare : 0
         * AduRewardDiscount : 0
         * AduEtdzFare : 0
         * AduRewardMoney : 0
         * ChdBaseDiscount : 0
         * ChdRewardDiscount : 0
         * ChdTaxFare : 0
         * ChdRewardTicketFare : 0
         * ChdEtdzFare : 0
         * ChdRewardMoney : 0
         */

        private String Flag;
        private String PlatCode;
        private String ErrMsg;
        private String IsVip;
        private String AccountLevel;
        private String TaxAmount;
        private String Price;
        private String SettlePrice;
        private String AduTaxFare;
        private String AduRewardTicketFare;
        private String AduRewardDiscount;
        private String AduEtdzFare;
        private String AduRewardMoney;
        private String ChdBaseDiscount;
        private String ChdRewardDiscount;
        private String ChdTaxFare;
        private String ChdRewardTicketFare;
        private String ChdEtdzFare;
        private String ChdRewardMoney;

        public String getFlag() {
            return Flag;
        }

        public void setFlag(String Flag) {
            this.Flag = Flag;
        }

        public String getPlatCode() {
            return PlatCode;
        }

        public void setPlatCode(String PlatCode) {
            this.PlatCode = PlatCode;
        }

        public String getErrMsg() {
            return ErrMsg;
        }

        public void setErrMsg(String ErrMsg) {
            this.ErrMsg = ErrMsg;
        }

        public String getIsVip() {
            return IsVip;
        }

        public void setIsVip(String IsVip) {
            this.IsVip = IsVip;
        }

        public String getAccountLevel() {
            return AccountLevel;
        }

        public void setAccountLevel(String AccountLevel) {
            this.AccountLevel = AccountLevel;
        }

        public String getTaxAmount() {
            return TaxAmount;
        }

        public void setTaxAmount(String TaxAmount) {
            this.TaxAmount = TaxAmount;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String Price) {
            this.Price = Price;
        }

        public String getSettlePrice() {
            return SettlePrice;
        }

        public void setSettlePrice(String SettlePrice) {
            this.SettlePrice = SettlePrice;
        }

        public String getAduTaxFare() {
            return AduTaxFare;
        }

        public void setAduTaxFare(String AduTaxFare) {
            this.AduTaxFare = AduTaxFare;
        }

        public String getAduRewardTicketFare() {
            return AduRewardTicketFare;
        }

        public void setAduRewardTicketFare(String AduRewardTicketFare) {
            this.AduRewardTicketFare = AduRewardTicketFare;
        }

        public String getAduRewardDiscount() {
            return AduRewardDiscount;
        }

        public void setAduRewardDiscount(String AduRewardDiscount) {
            this.AduRewardDiscount = AduRewardDiscount;
        }

        public String getAduEtdzFare() {
            return AduEtdzFare;
        }

        public void setAduEtdzFare(String AduEtdzFare) {
            this.AduEtdzFare = AduEtdzFare;
        }

        public String getAduRewardMoney() {
            return AduRewardMoney;
        }

        public void setAduRewardMoney(String AduRewardMoney) {
            this.AduRewardMoney = AduRewardMoney;
        }

        public String getChdBaseDiscount() {
            return ChdBaseDiscount;
        }

        public void setChdBaseDiscount(String ChdBaseDiscount) {
            this.ChdBaseDiscount = ChdBaseDiscount;
        }

        public String getChdRewardDiscount() {
            return ChdRewardDiscount;
        }

        public void setChdRewardDiscount(String ChdRewardDiscount) {
            this.ChdRewardDiscount = ChdRewardDiscount;
        }

        public String getChdTaxFare() {
            return ChdTaxFare;
        }

        public void setChdTaxFare(String ChdTaxFare) {
            this.ChdTaxFare = ChdTaxFare;
        }

        public String getChdRewardTicketFare() {
            return ChdRewardTicketFare;
        }

        public void setChdRewardTicketFare(String ChdRewardTicketFare) {
            this.ChdRewardTicketFare = ChdRewardTicketFare;
        }

        public String getChdEtdzFare() {
            return ChdEtdzFare;
        }

        public void setChdEtdzFare(String ChdEtdzFare) {
            this.ChdEtdzFare = ChdEtdzFare;
        }

        public String getChdRewardMoney() {
            return ChdRewardMoney;
        }

        public void setChdRewardMoney(String ChdRewardMoney) {
            this.ChdRewardMoney = ChdRewardMoney;
        }
    }
}
