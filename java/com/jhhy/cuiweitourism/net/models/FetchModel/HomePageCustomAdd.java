package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageCustomAdd extends BasicFetchModel {

    //{"startplace":"北京","dest":"夏威夷","starttime":"2016-8-30","days":"15",
    // "adultnum":"2","childnum":"0","yuesuan":"20000-50000","hotelrank":"豪华型",
    // "content":"备注","contactname":"李先生","phone":"15210656918","email":"A@A.com"}

    public String startplace;
    public String dest;
    public String starttime;
    public String days;
    public String adultnum;
    public String childnum;
    public String yuesuan;
    public String hotelrank;
    public String content;
    public String contactname;
    public String phone;
    public String email;

    public HomePageCustomAdd(String startplace, String dest, String starttime, String days, String adultnum, String childnum, String yuesuan, String hotelrank, String content, String contactname, String phone, String email) {
        this.startplace = startplace;
        this.dest = dest;
        this.starttime = starttime;
        this.days = days;
        this.adultnum = adultnum;
        this.childnum = childnum;
        this.yuesuan = yuesuan;
        this.hotelrank = hotelrank;
        this.content = content;
        this.contactname = contactname;
        this.phone = phone;
        this.email = email;
    }

    public HomePageCustomAdd() {
    }

    public String getStartplace() {
        return startplace;
    }

    public void setStartplace(String startplace) {
        this.startplace = startplace;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getAdultnum() {
        return adultnum;
    }

    public void setAdultnum(String adultnum) {
        this.adultnum = adultnum;
    }

    public String getChildnum() {
        return childnum;
    }

    public void setChildnum(String childnum) {
        this.childnum = childnum;
    }

    public String getYuesuan() {
        return yuesuan;
    }

    public void setYuesuan(String yuesuan) {
        this.yuesuan = yuesuan;
    }

    public String getHotelrank() {
        return hotelrank;
    }

    public void setHotelrank(String hotelrank) {
        this.hotelrank = hotelrank;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
