package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/2.
 */
public class CarRentPickLocation1  extends BasicFetchModel {

    public String  cityname;

    public CarRentPickLocation1(String cityname) {
        this.cityname = cityname;
    }

    public CarRentPickLocation1() {
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}
