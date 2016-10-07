package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/2.
 */
public class CarModel extends BasicFetchModel {

    //:{"cityid":"1","ruletype":"201"}

    public  String  cityid;
    public  String ruletype;

    public CarModel(String cityid, String ruletype) {
        this.cityid = cityid;
        this.ruletype = ruletype;
    }

    public CarModel() {
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getRuletype() {
        return ruletype;
    }

    public void setRuletype(String ruletype) {
        this.ruletype = ruletype;
    }
}
