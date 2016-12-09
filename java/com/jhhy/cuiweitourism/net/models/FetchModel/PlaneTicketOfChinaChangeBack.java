package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/9.
 */
public class PlaneTicketOfChinaChangeBack extends BasicFetchModel {

    /**
     * flightNo : CZ6132
     * classCode : E
     * depDate : 2016-12-17
     * depCode : PEK
     * arrCode : DLC
     */

    private String flightNo;
    private String classCode;
    private String depDate;
    private String depCode;
    private String arrCode;

    public PlaneTicketOfChinaChangeBack() {
    }

    public PlaneTicketOfChinaChangeBack(String flightNo, String classCode, String depDate, String depCode, String arrCode) {
        this.flightNo = flightNo;
        this.classCode = classCode;
        this.depDate = depDate;
        this.depCode = depCode;
        this.arrCode = arrCode;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getDepDate() {
        return depDate;
    }

    public void setDepDate(String depDate) {
        this.depDate = depDate;
    }

    public String getDepCode() {
        return depCode;
    }

    public void setDepCode(String depCode) {
        this.depCode = depCode;
    }

    public String getArrCode() {
        return arrCode;
    }

    public void setArrCode(String arrCode) {
        this.arrCode = arrCode;
    }
}
