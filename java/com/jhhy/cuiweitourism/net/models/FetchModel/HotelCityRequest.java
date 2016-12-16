package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/16.
 */
public class HotelCityRequest extends BasicFetchModel {

    /**
     * ProvinceID : 0100
     */

    private String ProvinceID;

    public HotelCityRequest(String provinceID) {
        ProvinceID = provinceID;
    }

    public String getProvinceID() {
        return ProvinceID;
    }

    public void setProvinceID(String ProvinceID) {
        this.ProvinceID = ProvinceID;
    }
}
