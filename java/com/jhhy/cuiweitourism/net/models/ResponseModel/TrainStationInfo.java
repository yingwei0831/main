package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/11.
 */
public class TrainStationInfo implements Serializable, Parcelable {

    public String id; // 车站序号
    public String name; //车站名
    public String fullPY; //车站名全拼
    public String shortPY; //车站名简拼
    public String headChar; //首字母
    public boolean isHot; //是否热点
    public int type ; //是否是标题

    public TrainStationInfo(String id, String name, String fullPY, String shortPY, String headChar, boolean isHot, int type) {
        this.id = id;
        this.name = name;
        this.fullPY = fullPY;
        this.shortPY = shortPY;
        this.headChar = headChar;
        this.isHot = isHot;
        this.type = type;
    }

    public TrainStationInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullPY() {
        return fullPY;
    }

    public void setFullPY(String fullPY) {
        this.fullPY = fullPY;
    }

    public String getShortPY() {
        return shortPY;
    }

    public void setShortPY(String shortPY) {
        this.shortPY = shortPY;
    }

    public boolean isHot() {
        return isHot;
    }

    public void setHot(boolean hot) {
        isHot = hot;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getHeadChar() {
        return headChar;
    }

    public void setHeadChar(String headChar) {
        this.headChar = headChar;
    }

    @Override
    public String toString() {
        return "TrainStationInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fullPY='" + fullPY + '\'' +
                ", shortPY='" + shortPY + '\'' +
                ", headChar='" + headChar + '\'' +
                ", isHot=" + isHot +
                ", type=" + type +
                '}';
    }


    public static final Parcelable.Creator<TrainStationInfo> CREATOR = new Creator<TrainStationInfo>() {
        @Override
        public TrainStationInfo createFromParcel(Parcel in) {
            return new TrainStationInfo(in);
        }

        @Override
        public TrainStationInfo[] newArray(int size) {
            return new TrainStationInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(fullPY);
        parcel.writeString(shortPY);
        parcel.writeString(headChar);
        parcel.writeByte((byte) (isHot ? 1 : 0));
        parcel.writeInt(type);
    }

    private TrainStationInfo(Parcel in){
        id = in.readString();
        name = in.readString();
        fullPY = in.readString();
        shortPY = in.readString();
        headChar = in.readString();
        isHot = in.readByte() != 0;
        type = in.readInt();
    }
}
