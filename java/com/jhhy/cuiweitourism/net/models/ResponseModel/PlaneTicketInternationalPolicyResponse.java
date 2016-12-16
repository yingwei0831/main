package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiahe008 on 2016/12/15.
 */
public class PlaneTicketInternationalPolicyResponse implements Serializable, Parcelable{ //

    /**
     * RequestID : OpenAPI92975461
     * ErrorCode : 0
     * ErrorMsg : []
     * ErrorStack : []
     * SearchInfo : {"TotalTime":"4.2299999999999995","TotalPlatforms":"1","SuccessPlatforms":"1","PolicyCount":"1"}
     * Policys : {
     *              "Policy":{
     *                  "Flag":"Y","PlatCode":"017","PolicyId":"2","PlatCodeForShort":"BY","PlatformMappingCode":"BY","PlatformName":"不夜城","IsVip":"10","AccountLevel":"10","PlatformType":"P",
     *                  "TktOffice":[],"SwitchPnr":"N","TaxAmount":"428","CommRate":"7.000","CommMoney":"0","TktType":"BSP",
     *                  "Remark":"1.客票变更请采购方自行处理,必须由供应商换开的每张加50元，退票50元，周末及节假日不处理。","TicketWorktime":"08:30-20:00","RefundWorkTime":[],"WastWorkTime":[],
     *                  "WeekendRefundTime":[],"Price":"1100","SettlePrice":"1023.0","AduTaxFare":"428","AduRewardTicketFare":"0","AduRewardDiscount":"0","AduEtdzFare":"0.0","AduRewardMoney":"0",
     *                  "ChdBaseDiscount":"0","ChdRewardDiscount":"0","ChdTaxFare":"338","ChdRewardTicketFare":"0","ChdEtdzFare":"0","ChdRewardMoney":"0",
     *                  "CommitKey":"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",
     *                  "ActualClassCode":"V"
     *              }
     *          }
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

    public List<String> getErrorMsg() {
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

    public static final Parcelable.Creator<PlaneTicketInternationalPolicyResponse> CREATOR = new Parcelable.Creator<PlaneTicketInternationalPolicyResponse>() {
        @Override
        public PlaneTicketInternationalPolicyResponse createFromParcel(Parcel in) {
            return new PlaneTicketInternationalPolicyResponse(in);
        }

        @Override
        public PlaneTicketInternationalPolicyResponse[] newArray(int size) {
            return new PlaneTicketInternationalPolicyResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(RequestID);
        out.writeString(ErrorCode);
        out.writeSerializable(SearchInfo);
        out.writeParcelable(Policys, flag);
        out.writeList(ErrorMsg);
        out.writeList(ErrorStack);
    }

    private PlaneTicketInternationalPolicyResponse(Parcel in){
        RequestID = in.readString();
        ErrorCode = in.readString();
        SearchInfo = in.readParcelable(SearchInfoBean.class.getClassLoader());
        Policys = in.readParcelable(PolicysBean.class.getClassLoader());
        if (ErrorMsg != null) {
            in.readStringList(ErrorMsg);
        }
        if (ErrorStack != null) {
            in.readStringList(ErrorStack);
        }
    }

    public static class SearchInfoBean implements Serializable, Parcelable{
        /**
         * TotalTime : 4.2299999999999995
         * TotalPlatforms : 1
         * SuccessPlatforms : 1
         * PolicyCount : 1
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

        public static final Parcelable.Creator<SearchInfoBean> CREATOR = new Parcelable.Creator<SearchInfoBean>() {
            @Override
            public SearchInfoBean createFromParcel(Parcel in) {
                return new SearchInfoBean(in);
            }

            @Override
            public SearchInfoBean[] newArray(int size) {
                return new SearchInfoBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            out.writeString(TotalTime);
            out.writeString(TotalPlatforms);
            out.writeString(SuccessPlatforms);
            out.writeString(PolicyCount);
        }

        private SearchInfoBean(Parcel in){
            TotalTime = in.readString();
            TotalPlatforms = in.readString();
            SuccessPlatforms = in.readString();
            PolicyCount = in.readString();
        }

        @Override
        public String toString() {
            return "SearchInfoBean{" +
                    "TotalTime='" + TotalTime + '\'' +
                    ", TotalPlatforms='" + TotalPlatforms + '\'' +
                    ", SuccessPlatforms='" + SuccessPlatforms + '\'' +
                    ", PolicyCount='" + PolicyCount + '\'' +
                    '}';
        }
    }

    public static class PolicysBean implements Serializable, Parcelable{
        /**
         * Policy : {"Flag":"Y","PlatCode":"017","PolicyId":"2","PlatCodeForShort":"BY","PlatformMappingCode":"BY","PlatformName":"不夜城","IsVip":"10","AccountLevel":"10","PlatformType":"P","TktOffice":[],"SwitchPnr":"N","TaxAmount":"428","CommRate":"7.000","CommMoney":"0","TktType":"BSP","Remark":"1.客票变更请采购方自行处理,必须由供应商换开的每张加50元，退票50元，周末及节假日不处理。","TicketWorktime":"08:30-20:00","RefundWorkTime":[],"WastWorkTime":[],"WeekendRefundTime":[],"Price":"1100","SettlePrice":"1023.0","AduTaxFare":"428","AduRewardTicketFare":"0","AduRewardDiscount":"0","AduEtdzFare":"0.0","AduRewardMoney":"0","ChdBaseDiscount":"0","ChdRewardDiscount":"0","ChdTaxFare":"338","ChdRewardTicketFare":"0","ChdEtdzFare":"0","ChdRewardMoney":"0","CommitKey":"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","ActualClassCode":"V"}
         */

        private PolicyBean Policy;

        public PolicyBean getPolicy() {
            return Policy;
        }

        public void setPolicy(PolicyBean Policy) {
            this.Policy = Policy;
        }

        public static final Parcelable.Creator<PolicysBean> CREATOR = new Parcelable.Creator<PolicysBean>() {
            @Override
            public PolicysBean createFromParcel(Parcel in) {
                return new PolicysBean(in);
            }

            @Override
            public PolicysBean[] newArray(int size) {
                return new PolicysBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            out.writeParcelable(Policy, flag);
        }

        private PolicysBean(Parcel in){
            Policy = in.readParcelable(PolicyBean.class.getClassLoader());
        }

        @Override
        public String toString() {
            return "PolicysBean{" +
                    "Policy=" + Policy +
                    '}';
        }
    }
    public static class PolicyBean implements Serializable, Parcelable{
        /**
         * Flag : Y
         * PlatCode : 017
         * PolicyId : 2
         * PlatCodeForShort : BY
         * PlatformMappingCode : BY
         * PlatformName : 不夜城
         * IsVip : 10
         * AccountLevel : 10
         * PlatformType : P
         * TktOffice : []
         * SwitchPnr : N
         * TaxAmount : 428
         * CommRate : 7.000
         * CommMoney : 0
         * TktType : BSP
         * Remark : 1.客票变更请采购方自行处理,必须由供应商换开的每张加50元，退票50元，周末及节假日不处理。
         * TicketWorktime : 08:30-20:00
         * RefundWorkTime : []
         * WastWorkTime : []
         * WeekendRefundTime : []
         * Price : 1100
         * SettlePrice : 1023.0
         * AduTaxFare : 428
         * AduRewardTicketFare : 0
         * AduRewardDiscount : 0
         * AduEtdzFare : 0.0
         * AduRewardMoney : 0
         * ChdBaseDiscount : 0
         * ChdRewardDiscount : 0
         * ChdTaxFare : 338
         * ChdRewardTicketFare : 0
         * ChdEtdzFare : 0
         * ChdRewardMoney : 0
         * CommitKey : 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
         * ActualClassCode : V
         */

        private String Flag;
        private String PlatCode;
        private String ErrMsg;
        private String PolicyId;
        private String PlatCodeForShort;
        private String PlatformMappingCode;
        private String PlatformName;
        private String IsVip;
        private String AccountLevel;
        private String PlatformType;
        private String SwitchPnr;
        private String TaxAmount;
        private String CommRate;
        private String CommMoney;
        private String TktType;
        private String Remark;
        private String TicketWorktime;
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
        private String CommitKey;
        private String ActualClassCode;
        private List<String> TktOffice;
        private List<String> RefundWorkTime;
        private List<String> WastWorkTime;
        private List<String> WeekendRefundTime;

        public String getFlag() {
            return Flag;
        }

        public String getErrMsg() {
            return ErrMsg;
        }

        public void setErrMsg(String errMsg) {
            ErrMsg = errMsg;
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

        public String getPolicyId() {
            return PolicyId;
        }

        public void setPolicyId(String PolicyId) {
            this.PolicyId = PolicyId;
        }

        public String getPlatCodeForShort() {
            return PlatCodeForShort;
        }

        public void setPlatCodeForShort(String PlatCodeForShort) {
            this.PlatCodeForShort = PlatCodeForShort;
        }

        public String getPlatformMappingCode() {
            return PlatformMappingCode;
        }

        public void setPlatformMappingCode(String PlatformMappingCode) {
            this.PlatformMappingCode = PlatformMappingCode;
        }

        public String getPlatformName() {
            return PlatformName;
        }

        public void setPlatformName(String PlatformName) {
            this.PlatformName = PlatformName;
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

        public String getPlatformType() {
            return PlatformType;
        }

        public void setPlatformType(String PlatformType) {
            this.PlatformType = PlatformType;
        }

        public String getSwitchPnr() {
            return SwitchPnr;
        }

        public void setSwitchPnr(String SwitchPnr) {
            this.SwitchPnr = SwitchPnr;
        }

        public String getTaxAmount() {
            return TaxAmount;
        }

        public void setTaxAmount(String TaxAmount) {
            this.TaxAmount = TaxAmount;
        }

        public String getCommRate() {
            return CommRate;
        }

        public void setCommRate(String CommRate) {
            this.CommRate = CommRate;
        }

        public String getCommMoney() {
            return CommMoney;
        }

        public void setCommMoney(String CommMoney) {
            this.CommMoney = CommMoney;
        }

        public String getTktType() {
            return TktType;
        }

        public void setTktType(String TktType) {
            this.TktType = TktType;
        }

        public String getRemark() {
            return Remark;
        }

        public void setRemark(String Remark) {
            this.Remark = Remark;
        }

        public String getTicketWorktime() {
            return TicketWorktime;
        }

        public void setTicketWorktime(String TicketWorktime) {
            this.TicketWorktime = TicketWorktime;
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

        public String getCommitKey() {
            return CommitKey;
        }

        public void setCommitKey(String CommitKey) {
            this.CommitKey = CommitKey;
        }

        public String getActualClassCode() {
            return ActualClassCode;
        }

        public void setActualClassCode(String ActualClassCode) {
            this.ActualClassCode = ActualClassCode;
        }

        public List<String> getTktOffice() {
            return TktOffice;
        }

        public void setTktOffice(List<String> TktOffice) {
            this.TktOffice = TktOffice;
        }

        public List<String> getRefundWorkTime() {
            return RefundWorkTime;
        }

        public void setRefundWorkTime(List<String> RefundWorkTime) {
            this.RefundWorkTime = RefundWorkTime;
        }

        public List<String> getWastWorkTime() {
            return WastWorkTime;
        }

        public void setWastWorkTime(List<String> WastWorkTime) {
            this.WastWorkTime = WastWorkTime;
        }

        public List<String> getWeekendRefundTime() {
            return WeekendRefundTime;
        }

        public void setWeekendRefundTime(List<String> WeekendRefundTime) {
            this.WeekendRefundTime = WeekendRefundTime;
        }

        public static final Parcelable.Creator<PolicyBean> CREATOR = new Parcelable.Creator<PolicyBean>() {
            @Override
            public PolicyBean createFromParcel(Parcel in) {
                return new PolicyBean(in);
            }

            @Override
            public PolicyBean[] newArray(int size) {
                return new PolicyBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            out.writeString(Flag);
            out.writeString(PlatCode);
            out.writeString(ErrMsg);
            out.writeString(PolicyId);
            out.writeString(PlatCodeForShort);
            out.writeString(PlatformMappingCode);
            out.writeString(PlatformName);
            out.writeString(IsVip);
            out.writeString(AccountLevel);
            out.writeString(PlatformType);
            out.writeString(SwitchPnr);
            out.writeString(TaxAmount);
            out.writeString(CommRate);
            out.writeString(CommMoney);
            out.writeString(TktType);
            out.writeString(Remark);
            out.writeString(TicketWorktime);
            out.writeString(Price);
            out.writeString(SettlePrice);
            out.writeString(AduTaxFare);
            out.writeString(AduRewardTicketFare);
            out.writeString(AduRewardDiscount);
            out.writeString(AduEtdzFare);
            out.writeString(AduRewardMoney);
            out.writeString(ChdBaseDiscount);
            out.writeString(ChdRewardDiscount);
            out.writeString(ChdTaxFare);
            out.writeString(ChdRewardTicketFare);
            out.writeString(ChdEtdzFare);
            out.writeString(ChdRewardMoney);
            out.writeString(CommitKey);
            out.writeString(ActualClassCode);
            out.writeStringList(TktOffice);
            out.writeStringList(RefundWorkTime);
            out.writeStringList(WastWorkTime);
            out.writeStringList(WeekendRefundTime);
        }

        private PolicyBean(Parcel in){
            Flag = in.readString();
            PlatCode = in.readString();
            ErrMsg = in.readString();
            PolicyId = in.readString();
            PlatCodeForShort = in.readString();
            PlatformMappingCode = in.readString();
            PlatformName = in.readString();
            IsVip = in.readString();
            AccountLevel = in.readString();
            PlatformType = in.readString();
            SwitchPnr = in.readString();
            TaxAmount = in.readString();
            CommRate = in.readString();
            CommMoney = in.readString();
            TktType = in.readString();
            Remark = in.readString();
            TicketWorktime = in.readString();
            Price = in.readString();
            SettlePrice = in.readString();
            AduTaxFare = in.readString();
            AduRewardTicketFare = in.readString();
            AduRewardDiscount = in.readString();
            AduEtdzFare = in.readString();
            AduRewardMoney = in.readString();
            ChdBaseDiscount = in.readString();
            ChdRewardDiscount = in.readString();
            ChdTaxFare = in.readString();
            ChdRewardTicketFare = in.readString();
            ChdEtdzFare = in.readString();
            ChdRewardMoney = in.readString();
            CommitKey = in.readString();
            ActualClassCode = in.readString();
            in.readStringList(TktOffice);
            in.readStringList(RefundWorkTime);
            in.readStringList(WastWorkTime);
            in.readStringList(WeekendRefundTime);
        }

        @Override
        public String toString() {
            return "PolicyBean{" +
                    "Flag='" + Flag + '\'' +
                    ", PlatCode='" + PlatCode + '\'' +
                    ", ErrMsg='" + ErrMsg + '\'' +
                    ", PolicyId='" + PolicyId + '\'' +
                    ", PlatCodeForShort='" + PlatCodeForShort + '\'' +
                    ", PlatformMappingCode='" + PlatformMappingCode + '\'' +
                    ", PlatformName='" + PlatformName + '\'' +
                    ", IsVip='" + IsVip + '\'' +
                    ", AccountLevel='" + AccountLevel + '\'' +
                    ", PlatformType='" + PlatformType + '\'' +
                    ", SwitchPnr='" + SwitchPnr + '\'' +
                    ", TaxAmount='" + TaxAmount + '\'' +
                    ", CommRate='" + CommRate + '\'' +
                    ", CommMoney='" + CommMoney + '\'' +
                    ", TktType='" + TktType + '\'' +
                    ", Remark='" + Remark + '\'' +
                    ", TicketWorktime='" + TicketWorktime + '\'' +
                    ", Price='" + Price + '\'' +
                    ", SettlePrice='" + SettlePrice + '\'' +
                    ", AduTaxFare='" + AduTaxFare + '\'' +
                    ", AduRewardTicketFare='" + AduRewardTicketFare + '\'' +
                    ", AduRewardDiscount='" + AduRewardDiscount + '\'' +
                    ", AduEtdzFare='" + AduEtdzFare + '\'' +
                    ", AduRewardMoney='" + AduRewardMoney + '\'' +
                    ", ChdBaseDiscount='" + ChdBaseDiscount + '\'' +
                    ", ChdRewardDiscount='" + ChdRewardDiscount + '\'' +
                    ", ChdTaxFare='" + ChdTaxFare + '\'' +
                    ", ChdRewardTicketFare='" + ChdRewardTicketFare + '\'' +
                    ", ChdEtdzFare='" + ChdEtdzFare + '\'' +
                    ", ChdRewardMoney='" + ChdRewardMoney + '\'' +
                    ", CommitKey='" + CommitKey + '\'' +
                    ", ActualClassCode='" + ActualClassCode + '\'' +
                    ", TktOffice=" + TktOffice +
                    ", RefundWorkTime=" + RefundWorkTime +
                    ", WastWorkTime=" + WastWorkTime +
                    ", WeekendRefundTime=" + WeekendRefundTime +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PlaneTicketInternationalPolicyResponse{" +
                "RequestID='" + RequestID + '\'' +
                ", ErrorCode='" + ErrorCode + '\'' +
                ", SearchInfo=" + SearchInfo +
                ", Policys=" + Policys +
                ", ErrorMsg=" + ErrorMsg +
                ", ErrorStack=" + ErrorStack +
                '}';
    }
}
