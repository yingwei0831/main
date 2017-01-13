package com.jhhy.cuiweitourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.jhhy.cuiweitourism.net.models.ResponseModel.PlaneTicketCityInfo;

/**
 * Created by jiahe008 on 2016/12/8.
 * 飞机票询价请求
 */
public class PlaneInquiry implements Parcelable{

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

    public static final Parcelable.Creator<PlaneInquiry> CREATOR = new Creator<PlaneInquiry>() {
        @Override
        public PlaneInquiry createFromParcel(Parcel in) {
            return new PlaneInquiry(in);
        }

        @Override
        public PlaneInquiry[] newArray(int size) {
            return new PlaneInquiry[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(fromDate);
        out.writeSerializable(fromCity);
        out.writeSerializable(arrivalCity);
    }

    private PlaneInquiry(Parcel in){
        fromDate = in.readString();
        fromCity = (PlaneTicketCityInfo) in.readSerializable();
        arrivalCity = (PlaneTicketCityInfo) in.readSerializable();
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
