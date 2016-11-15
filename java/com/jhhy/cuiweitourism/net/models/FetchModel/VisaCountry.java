package com.jhhy.cuiweitourism.net.models.FetchModel;


/**
 * Created by zhangguang on 16/10/9.
 */
public class VisaCountry extends  BasicFetchModel {

    //    {"head":{"code":"Visa_getvisacountry"},"field":{"IsHot":"N","DeltaCode":"","PltType":"P"}}
    public String IsHot; //IsHot:是否热门 Y:是N:否(查询所有)不填默认为N;
    public String DeltaCode; //
    public String PltType; //PltType:平台代码P; A: 安卓;I: 苹果;不填默认为P;

    public VisaCountry() {
    }

    public VisaCountry(String isHot, String deltaCode, String pltType) {
        IsHot = isHot;
        DeltaCode = deltaCode;
        PltType = pltType;
    }

    public String getIsHot() {
        return IsHot;
    }

    public void setIsHot(String isHot) {
        IsHot = isHot;
    }

    public String getDeltaCode() {
        return DeltaCode;
    }

    public void setDeltaCode(String deltaCode) {
        DeltaCode = deltaCode;
    }

    public String getPltType() {
        return PltType;
    }

    public void setPltType(String pltType) {
        PltType = pltType;
    }

}
