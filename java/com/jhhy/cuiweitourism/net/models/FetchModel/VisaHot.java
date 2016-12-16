package com.jhhy.cuiweitourism.net.models.FetchModel;


/**
 * Created by birneysky on 16/10/9.
 */
public class VisaHot extends  BasicFetchModel {

    //   "CountryCode":"","DistributionArea":"","PltType":"A"
    public String CountryCode; //CountryCode:国家二字码 默认为空 ;
    public String DistributionArea; //送签地(城市三字码)
    public String PltType; //PltType:平台代码P; A: 安卓;I: 苹果;不填默认为P;

    public VisaHot() {
    }

    public VisaHot(String countryCode, String distributionArea, String pltType) {
        CountryCode = countryCode;
        DistributionArea = distributionArea;
        PltType = pltType;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getDistributionArea() {
        return DistributionArea;
    }

    public void setDistributionArea(String distributionArea) {
        DistributionArea = distributionArea;
    }

    public String getPltType() {
        return PltType;
    }

    public void setPltType(String pltType) {
        PltType = pltType;
    }
}
