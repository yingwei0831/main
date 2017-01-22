package com.jhhy.cuiweitourism.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/9/20.
 * 联系人
 */
public class UserContacts implements Parcelable, Serializable
{
//    "id":"19","memberid":"1","linkman":"李","mobile":"15933424245","idcard":"哥哥哥哥","cardtype":"身份证","sex":"0","passport":null
    private String contactsId; //联系人id，联系人在数据库中的id
    private String contactsMemberId; //谁的常用联系人，就是谁的id，即userId
    private String contactsName; //姓名
    private String contactsMobile; //手机号码
    private String contactsIdCard; //身份证号码
    private String contactsCardType; //证件类型
    private String contactsGender; //性别 0:女，1:男
    private String contactsPassport; //护照号码
    private String EnglishName;

    public UserContacts() {
    }

    public String getEnglishName() {
        return EnglishName;
    }

    public void setEnglishName(String englishName) {
        EnglishName = englishName;
    }

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getContactsMemberId() {
        return contactsMemberId;
    }

    public void setContactsMemberId(String contactsMemberId) {
        this.contactsMemberId = contactsMemberId;
    }

    public String getContactsName() {
        return contactsName;
    }

    public void setContactsName(String contactsName) {
        this.contactsName = contactsName;
    }

    public String getContactsMobile() {
        return contactsMobile;
    }

    public void setContactsMobile(String contactsMobile) {
        this.contactsMobile = contactsMobile;
    }

    public String getContactsIdCard() {
        return contactsIdCard;
    }

    public void setContactsIdCard(String contactsIdCard) {
        this.contactsIdCard = contactsIdCard;
    }

    public String getContactsCardType() {
        return contactsCardType;
    }

    public void setContactsCardType(String contactsCardType) {
        this.contactsCardType = contactsCardType;
    }

    public String getContactsGender() {
        return contactsGender;
    }

    public void setContactsGender(String contactsGender) {
        this.contactsGender = contactsGender;
    }

    public String getContactsPassport() {
        return contactsPassport;
    }

    public void setContactsPassport(String contactsPassport) {
        this.contactsPassport = contactsPassport;
    }



    public static final Parcelable.Creator<UserContacts> CREATOR = new Creator<UserContacts>() {
        @Override
        public UserContacts createFromParcel(Parcel in) {
            return new UserContacts(in);
        }

        @Override
        public UserContacts[] newArray(int size) {
            return new UserContacts[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeString(contactsId);
        out.writeString(contactsName);
        out.writeString(contactsMobile);
        out.writeString(contactsIdCard);
        out.writeString(contactsCardType);
        out.writeString(contactsGender);
        out.writeString(contactsPassport);
        out.writeString(EnglishName);
    }

    private UserContacts(Parcel in){
        contactsId = in.readString();
        contactsName = in.readString();
        contactsMobile = in.readString();
        contactsIdCard = in.readString();
        contactsCardType = in.readString();
        contactsGender = in.readString();
        contactsPassport = in.readString();
        EnglishName = in.readString();
    }

    @Override
    public String toString() {
        return "UserContacts{" +
                "contactsId='" + contactsId + '\'' +
                ", contactsMemberId='" + contactsMemberId + '\'' +
                ", contactsName='" + contactsName + '\'' +
                ", contactsMobile='" + contactsMobile + '\'' +
                ", contactsIdCard='" + contactsIdCard + '\'' +
                ", contactsCardType='" + contactsCardType + '\'' +
                ", contactsGender='" + contactsGender + '\'' +
                ", contactsPassport='" + contactsPassport + '\'' +
                '}';
    }
}
