package com.jhhy.cuiweitourism.model;

import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;

/**
 * Created by jiahe008 on 2016/12/8.
 * 飞机票询价请求
 */
public class PlaneInquiry {

    public PlaneTicketCityInfo fromCity;
    public PlaneTicketCityInfo arrivalCity;
    public String fromDate;

    public PlaneInquiry() {
    }

    public PlaneInquiry(PlaneTicketCityInfo fromCity, PlaneTicketCityInfo arrivalCity, String fromDate) {
        this.fromCity = fromCity;
        arrivalCity = arrivalCity;
        this.fromDate = fromDate;
    }

    public PlaneTicketCityInfo getFromCity() {
        return fromCity;
    }

    public void setFromCity(PlaneTicketCityInfo fromCity) {
        this.fromCity = fromCity;
    }

    public PlaneTicketCityInfo getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(PlaneTicketCityInfo arrivalCity) {
        arrivalCity = arrivalCity;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    @Override
    public String toString() {
        return "PlaneInquiry{" +
                "fromCity=" + fromCity +
                ", ArrivalCity=" + arrivalCity +
                ", fromDate='" + fromDate + '\'' +
                '}';
    }
}
