package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-02.
 */
public class CarRentCity  extends BasicFetchModel {
//field":{"city":""}

    public String city;

    public CarRentCity(String city) {
        this.city = city;
    }

    public CarRentCity() {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
