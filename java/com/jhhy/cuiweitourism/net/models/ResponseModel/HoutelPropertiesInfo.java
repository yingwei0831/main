package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/17.
 */
public class HoutelPropertiesInfo implements Serializable {
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel out, int flag) {
//        out.writeString(id);
//        out.writeString(attrname);
//        out.writeList(son);
//    }
//    private HoutelPropertiesInfo(Parcel in){
//        id = in.readString();
//        attrname = in.readString();
////        son = in.readArrayList(son.getClass().getClassLoader());
//    }
//    public static final Parcelable.Creator<HoutelPropertiesInfo> CREATOR = new Creator<HoutelPropertiesInfo>() {
//        @Override
//        public HoutelPropertiesInfo createFromParcel(Parcel in) {
//            return new HoutelPropertiesInfo(in);
//        }
//
//        @Override
//        public HoutelPropertiesInfo[] newArray(int size) {
//            return new HoutelPropertiesInfo[size];
//        }
//    };


//    {
//        "id": "190",
//            "attrname": "品牌",
//            "son": [
//        {
//            "id": "191",
//                "attrname": "品牌1"
//        }
//        ]
//    },


    public static class Son implements Parcelable{
        public String id;
        public String attrname;

        public Son(String id, String attrname) {
            this.id = id;
            this.attrname = attrname;
        }

        public Son() {
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAttrname() {
            return attrname;
        }

        public void setAttrname(String attrname) {
            this.attrname = attrname;
        }

        @Override
        public String toString() {
            return "Son{" +
                    "id='" + id + '\'' +
                    ", attrname='" + attrname + '\'' +
                    '}';
        }

        public static final Parcelable.Creator<Son> CREATOR = new Creator<Son>() {
            @Override
            public Son createFromParcel(Parcel in) {
                return new Son(in);
            }

            @Override
            public Son[] newArray(int size) {
                return new Son[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(id);
            parcel.writeString(attrname);
        }
        private Son(Parcel in){
            id = in.readString();
            attrname = in.readString();
        }

    }

    public String id;
    public String attrname;
    public ArrayList<Son> son;

    public HoutelPropertiesInfo(String id, String attrname, ArrayList<Son> son) {
        this.id = id;
        this.attrname = attrname;
        this.son = son;
    }

    public HoutelPropertiesInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttrname() {
        return attrname;
    }

    public void setAttrname(String attrname) {
        this.attrname = attrname;
    }

    public ArrayList<Son> getSon() {
        return son;
    }

    public void setSon(ArrayList<Son> son) {
        this.son = son;
    }

    @Override
    public String toString() {
        return "HoutelPropertiesInfo{" +
                "id='" + id + '\'' +
                ", attrname='" + attrname + '\'' +
                ", son=" + son +
                '}';
    }
}
