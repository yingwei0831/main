package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/16.
 */
public class HotelProvinceResponse implements Parcelable{

    private List<ProvinceBean> Item;

    public HotelProvinceResponse() {
    }

    public HotelProvinceResponse(List<ProvinceBean> item) {
        Item = item;
    }

    public List<ProvinceBean> getItem() {
        return Item;
    }

    public void setItem(List<ProvinceBean> Item) {
        this.Item = Item;
    }

    public static final Parcelable.Creator<HotelProvinceResponse> CREATOR = new Parcelable.Creator<HotelProvinceResponse>() {
        @Override
        public HotelProvinceResponse createFromParcel(Parcel in) {
            return new HotelProvinceResponse(in);
        }

        @Override
        public HotelProvinceResponse[] newArray(int size) {
            return new HotelProvinceResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeList(Item);
    }

    private HotelProvinceResponse(Parcel in){
        if (Item != null) {
            in.readList(Item, ProvinceBean.class.getClassLoader());
        }
    }

    public static class ProvinceBean implements Parcelable{
        /**请求省份返回城市
         * Code : 5448
         * IsDomc : D
         * IsHot : N
         * IsShow : N
         * JianPin : BH
         * Name : 博湖
         * QuanPin : BoHu
         */
        private String Code;
        private String IsDomc;
        private String IsHot;
        private String IsShow;

        /**请求省份
         * ID : 3800
         * Name : 首尔特别市
         */
        private String ID;
        private String Name;
        private String QuanPin;
        private String JianPin;
        private String headChar;
        private int type;

        public ProvinceBean() {
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }

        public String getIsDomc() {
            return IsDomc;
        }

        public void setIsDomc(String isDomc) {
            IsDomc = isDomc;
        }

        public String getIsHot() {
            return IsHot;
        }

        public void setIsHot(String isHot) {
            IsHot = isHot;
        }

        public String getIsShow() {
            return IsShow;
        }

        public void setIsShow(String isShow) {
            IsShow = isShow;
        }

        public String getQuanPin() {
            return QuanPin;
        }

        public void setQuanPin(String quanPin) {
            QuanPin = quanPin;
        }

        public String getJianPin() {
            return JianPin;
        }

        public void setJianPin(String jianPin) {
            JianPin = jianPin;
        }

        public String getHeadChar() {
            return headChar;
        }

        public void setHeadChar(String headChar) {
            this.headChar = headChar;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        @Override
        public String toString() {
            return "ProvinceBean{" +
                    "Code='" + Code + '\'' +
                    ", IsDomc='" + IsDomc + '\'' +
                    ", IsHot='" + IsHot + '\'' +
                    ", IsShow='" + IsShow + '\'' +
                    ", ID='" + ID + '\'' +
                    ", Name='" + Name + '\'' +
                    ", QuanPin='" + QuanPin + '\'' +
                    ", JianPin='" + JianPin + '\'' +
                    ", headChar='" + headChar + '\'' +
                    ", type=" + type +
                    '}';
        }

        public static final Parcelable.Creator<ProvinceBean> CREATOR = new Parcelable.Creator<ProvinceBean>() {
            @Override
            public ProvinceBean createFromParcel(Parcel in) {
                return new ProvinceBean(in);
            }

            @Override
            public ProvinceBean[] newArray(int size) {
                return new ProvinceBean[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flag) {
            out.writeString(ID);
            out.writeString(Name);
            out.writeString(QuanPin);
            out.writeString(JianPin);
//            out.writeString(headChar);
//            out.writeInt(type);
            out.writeString(Code);
            out.writeString(IsDomc);
            out.writeString(IsHot);
            out.writeString(IsShow);
        }

        private ProvinceBean(Parcel in){
            ID = in.readString();
            Name = in.readString();
            QuanPin = in.readString();
            JianPin = in.readString();
//            headChar = in.readString();
//            type = in.readInt();
            Code = in.readString();
            IsDomc = in.readString();
            IsHot = in.readString();
            IsShow = in.readString();
        }
    }
}
