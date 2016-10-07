package com.jhhy.cuiweitourism.moudle;

/**
 * Created by birney1 on 16/9/12.
 * 热门目的地
 */
public class HotDestination {

    private String cityId;
    private String cityName;

    public HotDestination() {
    }

    public HotDestination(String cityId, String cityName) {
        this.cityId = cityId;
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
