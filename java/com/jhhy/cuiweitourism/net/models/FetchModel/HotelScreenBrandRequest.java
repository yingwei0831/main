package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/26.
 */
public class HotelScreenBrandRequest extends BasicFetchModel {

    /**
     * CityCode : 0101
     */

    private String CityCode;

    public HotelScreenBrandRequest(String cityCode) {
        CityCode = cityCode;
    }

    public String getCityCode() {
        return CityCode;
    }

    public void setCityCode(String CityCode) {
        this.CityCode = CityCode;
    }
}
