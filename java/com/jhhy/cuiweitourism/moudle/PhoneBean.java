package com.jhhy.cuiweitourism.moudle;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.jhhy.cuiweitourism.utils.HanziToPinyin;

import java.io.Serializable;
import java.util.ArrayList;

public class PhoneBean implements Serializable, Parcelable{
    /**
     * 城市名字首字母
     */
    private String headChar;
    /**
     * 城市id
     */
    private String city_id;
    /**
     * 省id
     */
    private String pro_id;
    /**
     * 城市名字
     */
    private String name;
    /**
     * 城市字母名字
     */
    private String name_en;
    /**
     * 是否是标题
     */
    private int type;

    public String getHeadChar() {
        return headChar;
    }

    public String getName() {
        return name;
    }

    public String getName_en() {
        return name_en;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
        name_en = getPinYin(name);//获取字母名称
        name_en = name_en.toUpperCase();//把小写字母换成大写字母
        if (!TextUtils.isEmpty(name_en)) {
            char head = name_en.charAt(0);
            if (head < 'A' || head > 'Z') {
                head = '#';
            }
            headChar = head + "";
        }
    }

    /**
     * 汉字转换拼音，字母原样返回，都转换为小写
     */
    public String getPinYin(String input) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (token.type == HanziToPinyin.Token.PINYIN) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    public PhoneBean() {
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getPro_id() {
        return pro_id;
    }

    public void setPro_id(String pro_id) {
        this.pro_id = pro_id;
    }



    public static final Parcelable.Creator<PhoneBean> CREATOR = new Creator<PhoneBean>() {
        @Override
        public PhoneBean createFromParcel(Parcel in) {
            return new PhoneBean(in);
        }

        @Override
        public PhoneBean[] newArray(int size) {
            return new PhoneBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(headChar);
        out.writeString(city_id);
        out.writeString(pro_id);
        out.writeString(name);
        out.writeString(name_en);
        out.writeInt(   type);
    }

    private PhoneBean(Parcel in){
        headChar = in.readString();
        city_id = in.readString();
        pro_id = in.readString();
        name = in.readString();
        name_en  = in.readString();
        type = in.readInt();
    }
}
