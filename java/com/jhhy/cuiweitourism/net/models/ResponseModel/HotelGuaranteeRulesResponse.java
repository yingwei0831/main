package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/12/30.
 * 担保规则返回数据
 */
public class HotelGuaranteeRulesResponse {

    /**
     * ErrorCode : 0
     * ErrorMsg :
     * GuaranteeRules :
     * IsGuaranteeRule : false
     */

    private String ErrorCode;
    private String ErrorMsg;
    private String GuaranteeRules;
    private String IsGuaranteeRule;

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

    public String getGuaranteeRules() {
        return GuaranteeRules;
    }

    public void setGuaranteeRules(String GuaranteeRules) {
        this.GuaranteeRules = GuaranteeRules;
    }

    public String getIsGuaranteeRule() {
        return IsGuaranteeRule;
    }

    public void setIsGuaranteeRule(String IsGuaranteeRule) {
        this.IsGuaranteeRule = IsGuaranteeRule;
    }
}
