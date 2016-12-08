package com.jhhy.cuiweitourism.model;

/**
 * Created by jiahe008 on 2016/8/24.
 * 热门推荐，换一换
 */
public class CityRecommend {

    private String cityId;
    private String cityName; //城市名字
    private String cityImage; //城市图片

    public CityRecommend() {
    }

    public CityRecommend(String cityId, String cityName, String cityImage) {
        this.cityId = cityId;
        this.cityName = cityName;
        this.cityImage = cityImage;
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

    public String getCityImage() {
        return cityImage;
    }

    public void setCityImage(String cityImage) {
        this.cityImage = cityImage;
    }
}
