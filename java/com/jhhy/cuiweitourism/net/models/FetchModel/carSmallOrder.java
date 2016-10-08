package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/2.
 */
public class carSmallOrder extends BasicFetchModel {

    //{"memberid":"1","linkman":"张松钠","linktel":"13898698745",
    // "rtrule":"201","rttype":"0","rtStartcitycode":"1","
    // rtStartcityname":"北京市","rtstartflat":"40.081115580237",
    // "rtstartflng":"116.58797959531","rtstartname":"首都机场",
    // "rtstartaddress":"首都机场1号航站楼","RtEndCityCode":"1",
    // "RtEndCityName":"北京市","RtEndtlat":"39.96956",
    // "RtEndtlng":"116.40029","RtEndName":"浙江大厦",
    // "RtEndAddress":"朝阳区北三环中路安贞西里三区26号",
    // "RtDepartureTime":"2016-09-16 12:00:00",
    // "RtRequireLevel":"100","RtAppTime":"2016-09-9 12:00:00",
    // "RtPriceCarCode":"100","RtPriceCarName":"舒适型:大众帕萨特、凯美瑞等",
    // "RtPrice":"200","RtStartPrice":"12",
    // "RtNormalUnitPrice":"2.9","RtLineType":"JJ"}}

    public String memberid;
    public String linkman;
    public String linktel;
    public String rtrule;
    public String rttype;
    public String rtStartcitycode;
    public String rtStartcityname;
    public String rtstartflat;
    public String rtstartflng;
    public String rtstartname;
    public String rtstartaddress;
    public String RtEndCityCode;
    public String RtEndCityName;
    public String RtEndtlat;
    public String RtEndtlng;
    public String  RtEndName;
    public String RtEndAddress;
    public String RtDepartureTime;
    public String RtRequireLevel;
    public String RtAppTime;
    public String RtPriceCarCode;
    public String RtPriceCarName;
    public String RtPrice;
    public String RtStartPrice;
    public String RtNormalUnitPrice;
    public String RtLineType;


    public carSmallOrder(String memberid, String linkman, String linktel, String rtrule, String rttype, String rtStartcitycode, String rtStartcityname, String rtstartflat, String rtstartflng, String rtstartname, String rtstartaddress, String rtEndCityCode, String rtEndCityName, String rtEndtlat, String rtEndtlng, String rtEndName, String rtEndAddress, String rtDepartureTime, String rtRequireLevel, String rtAppTime, String rtPriceCarCode, String rtPriceCarName, String rtPrice, String rtStartPrice, String rtNormalUnitPrice, String rtLineType) {
        this.memberid = memberid;
        this.linkman = linkman;
        this.linktel = linktel;
        this.rtrule = rtrule;
        this.rttype = rttype;
        this.rtStartcitycode = rtStartcitycode;
        this.rtStartcityname = rtStartcityname;
        this.rtstartflat = rtstartflat;
        this.rtstartflng = rtstartflng;
        this.rtstartname = rtstartname;
        this.rtstartaddress = rtstartaddress;
        RtEndCityCode = rtEndCityCode;
        RtEndCityName = rtEndCityName;
        RtEndtlat = rtEndtlat;
        RtEndtlng = rtEndtlng;
        RtEndName = rtEndName;
        RtEndAddress = rtEndAddress;
        RtDepartureTime = rtDepartureTime;
        RtRequireLevel = rtRequireLevel;
        RtAppTime = rtAppTime;
        RtPriceCarCode = rtPriceCarCode;
        RtPriceCarName = rtPriceCarName;
        RtPrice = rtPrice;
        RtStartPrice = rtStartPrice;
        RtNormalUnitPrice = rtNormalUnitPrice;
        RtLineType = rtLineType;
    }

    public carSmallOrder() {
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }

    public String getRtrule() {
        return rtrule;
    }

    public void setRtrule(String rtrule) {
        this.rtrule = rtrule;
    }

    public String getRttype() {
        return rttype;
    }

    public void setRttype(String rttype) {
        this.rttype = rttype;
    }

    public String getRtStartcitycode() {
        return rtStartcitycode;
    }

    public void setRtStartcitycode(String rtStartcitycode) {
        this.rtStartcitycode = rtStartcitycode;
    }

    public String getRtStartcityname() {
        return rtStartcityname;
    }

    public void setRtStartcityname(String rtStartcityname) {
        this.rtStartcityname = rtStartcityname;
    }

    public String getRtstartflat() {
        return rtstartflat;
    }

    public void setRtstartflat(String rtstartflat) {
        this.rtstartflat = rtstartflat;
    }

    public String getRtstartflng() {
        return rtstartflng;
    }

    public void setRtstartflng(String rtstartflng) {
        this.rtstartflng = rtstartflng;
    }

    public String getRtstartname() {
        return rtstartname;
    }

    public void setRtstartname(String rtstartname) {
        this.rtstartname = rtstartname;
    }

    public String getRtstartaddress() {
        return rtstartaddress;
    }

    public void setRtstartaddress(String rtstartaddress) {
        this.rtstartaddress = rtstartaddress;
    }

    public String getRtEndCityCode() {
        return RtEndCityCode;
    }

    public void setRtEndCityCode(String rtEndCityCode) {
        RtEndCityCode = rtEndCityCode;
    }

    public String getRtEndCityName() {
        return RtEndCityName;
    }

    public void setRtEndCityName(String rtEndCityName) {
        RtEndCityName = rtEndCityName;
    }

    public String getRtEndtlat() {
        return RtEndtlat;
    }

    public void setRtEndtlat(String rtEndtlat) {
        RtEndtlat = rtEndtlat;
    }

    public String getRtEndtlng() {
        return RtEndtlng;
    }

    public void setRtEndtlng(String rtEndtlng) {
        RtEndtlng = rtEndtlng;
    }

    public String getRtEndName() {
        return RtEndName;
    }

    public void setRtEndName(String rtEndName) {
        RtEndName = rtEndName;
    }

    public String getRtEndAddress() {
        return RtEndAddress;
    }

    public void setRtEndAddress(String rtEndAddress) {
        RtEndAddress = rtEndAddress;
    }

    public String getRtDepartureTime() {
        return RtDepartureTime;
    }

    public void setRtDepartureTime(String rtDepartureTime) {
        RtDepartureTime = rtDepartureTime;
    }

    public String getRtRequireLevel() {
        return RtRequireLevel;
    }

    public void setRtRequireLevel(String rtRequireLevel) {
        RtRequireLevel = rtRequireLevel;
    }

    public String getRtAppTime() {
        return RtAppTime;
    }

    public void setRtAppTime(String rtAppTime) {
        RtAppTime = rtAppTime;
    }

    public String getRtPriceCarCode() {
        return RtPriceCarCode;
    }

    public void setRtPriceCarCode(String rtPriceCarCode) {
        RtPriceCarCode = rtPriceCarCode;
    }

    public String getRtPriceCarName() {
        return RtPriceCarName;
    }

    public void setRtPriceCarName(String rtPriceCarName) {
        RtPriceCarName = rtPriceCarName;
    }

    public String getRtPrice() {
        return RtPrice;
    }

    public void setRtPrice(String rtPrice) {
        RtPrice = rtPrice;
    }

    public String getRtStartPrice() {
        return RtStartPrice;
    }

    public void setRtStartPrice(String rtStartPrice) {
        RtStartPrice = rtStartPrice;
    }

    public String getRtNormalUnitPrice() {
        return RtNormalUnitPrice;
    }

    public void setRtNormalUnitPrice(String rtNormalUnitPrice) {
        RtNormalUnitPrice = rtNormalUnitPrice;
    }

    public String getRtLineType() {
        return RtLineType;
    }

    public void setRtLineType(String rtLineType) {
        RtLineType = rtLineType;
    }
}
