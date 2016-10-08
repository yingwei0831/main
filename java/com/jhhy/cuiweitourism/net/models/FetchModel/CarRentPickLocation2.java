package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/2.
 */
public class CarRentPickLocation2 extends BasicFetchModel {

    public  String address;
    public String  cityname;

    public CarRentPickLocation2(String cityname,String address) {
        this.address = address;
        this.cityname = cityname;
    }

    public CarRentPickLocation2() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }
}

