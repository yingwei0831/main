package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/2.
 */
public class CarPriceEstimate extends BasicFetchModel {

    //{"fromlat":"40.081115580237","fromlng":"116.58797959531",
    // "arrivelat":"39.96956","arrivelng":"116.40029","ruletype":"201","cityid":"1"
    public String fromlat;
    public String fromlng;
    public String arrivelat;
    public String arrivelng;
    public String ruletype;
    public String cityid;

    public CarPriceEstimate(String fromlat, String fromlng, String arrivelat, String arrivelng, String ruletype, String cityid) {
        this.fromlat = fromlat;
        this.fromlng = fromlng;
        this.arrivelat = arrivelat;
        this.arrivelng = arrivelng;
        this.ruletype = ruletype;
        this.cityid = cityid;
    }

    public CarPriceEstimate() {
    }

    public String getFromlat() {
        return fromlat;
    }

    public void setFromlat(String fromlat) {
        this.fromlat = fromlat;
    }

    public String getFromlng() {
        return fromlng;
    }

    public void setFromlng(String fromlng) {
        this.fromlng = fromlng;
    }

    public String getArrivelat() {
        return arrivelat;
    }

    public void setArrivelat(String arrivelat) {
        this.arrivelat = arrivelat;
    }

    public String getArrivelng() {
        return arrivelng;
    }

    public void setArrivelng(String arrivelng) {
        this.arrivelng = arrivelng;
    }

    public String getRuletype() {
        return ruletype;
    }

    public void setRuletype(String ruletype) {
        this.ruletype = ruletype;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }
}
