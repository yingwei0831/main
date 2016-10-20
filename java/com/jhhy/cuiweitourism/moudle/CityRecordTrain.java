package com.jhhy.cuiweitourism.moudle;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.jhhy.cuiweitourism.utils.HanziToPinyin;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/10/20.
 * 历史记录，火车站选择
 */
public class CityRecordTrain implements Serializable, Parcelable {

    private String cityId;
    private String cityName;
    private String cityFullPinyin;
    private String cityJianPinyin;
    private String cityHot;
    /**
     * 城市名字首字母
     */
    private String headChar;
    /**
     * 是否是标题
     */
    private int type;

    public CityRecordTrain() {
    }
    public String getHeadChar() {
        return headChar;
    }

    public String getCityName() {
        return cityName;
    }
    public String getCityFullPinyin() {
        return cityFullPinyin;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setCityFullPinyin(String cityFullPinyin) {
        this.cityFullPinyin = cityFullPinyin.toUpperCase();//把小写字母换成大写字母
        if (!TextUtils.isEmpty(this.cityFullPinyin)) {
            char head = this.cityFullPinyin.charAt(0);
            if (head < 'A' || head > 'Z') {
                head = '#';
            }
            headChar = head + "";
        }
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityJianPinyin() {
        return cityJianPinyin;
    }

    public void setCityJianPinyin(String cityJianPinyin) {
        this.cityJianPinyin = cityJianPinyin;
    }

    public String getCityHot() {
        return cityHot;
    }

    public void setCityHot(String cityHot) {
        this.cityHot = cityHot;
    }


    public static final Parcelable.Creator<CityRecordTrain> CREATOR = new Parcelable.Creator<CityRecordTrain>() {
        @Override
        public CityRecordTrain createFromParcel(Parcel in) {
            return new CityRecordTrain(in);
        }

        @Override
        public CityRecordTrain[] newArray(int size) {
            return new CityRecordTrain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(headChar);
        out.writeString(cityId);
        out.writeString(cityJianPinyin);
        out.writeString(cityName);
        out.writeString(cityFullPinyin);
        out.writeString(cityHot);
        out.writeInt(   type);
    }

    private CityRecordTrain(Parcel in){
        headChar = in.readString();
        cityId = in.readString();
        cityJianPinyin = in.readString();
        cityName = in.readString();
        cityFullPinyin  = in.readString();
        cityHot  = in.readString();
        type = in.readInt();
    }
}
