package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney on 2016-10-24.
 */
public class PlanTicketCityInfo {
//    "id": "1",
//            "code": "PEK",
//            "name": "北京",
//            "airportname": "北京首都国际机场",
//            "isdomc": "D"

    public String  id;
    public String  code;
    public String  name;
    public String  airportname;
    public String  isdomc;

    public String fullPY; //车站名全拼
    public String shortPY; //车站名简拼
    public String headChar;//
    public int type ; //是否是标题

    public PlanTicketCityInfo(String id, String code, String name, String airportname, String isdomc) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.airportname = airportname;
        this.isdomc = isdomc;
    }

    public PlanTicketCityInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirportname() {
        return airportname;
    }

    public void setAirportname(String airportname) {
        this.airportname = airportname;
    }

    public String getIsdomc() {
        return isdomc;
    }

    public void setIsdomc(String isdomc) {
        this.isdomc = isdomc;
    }

    @Override
    public String toString() {
        return "PlanTicketCityInfo{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", airportname='" + airportname + '\'' +
                ", isdomc='" + isdomc + '\'' +
                ", fullPY='" + fullPY + '\'' +
                ", shortPY='" + shortPY + '\'' +
                ", headChar='" + headChar + '\'' +
                ", type=" + type +
                '}';
    }
}
