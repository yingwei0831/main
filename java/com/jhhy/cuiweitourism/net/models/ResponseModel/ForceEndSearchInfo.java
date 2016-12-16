package com.jhhy.cuiweitourism.net.models.ResponseModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by birneysky on 16/10/10.
 */
public class ForceEndSearchInfo {

//    "channelname": "线路",
//    "webid": "0",
//    "aid": "1",
//    "typeid": "1",
//    "title": "北京5日游",
//    "description": "<p>北京五日游</p>",
//    "litpic": "http://cwly.taskbees.cn:8083/uploads/2016/0607/9329dff285092f3874aed5e814d437c3.jpg",
//    "shownum": "18",
//    "kindlist": "1,7,8",
//    "attrid": "45,1",
//    "headimgid": "0",
//    "tid": "12",
//    "ishidden": "0"

//    {
//        "txt": "跟团游",
//        "id": "193",
//        "title": "椰风醉心游 (海口往返4晚5天)",
//        "litpic": "http://www.cwly1118.com/uploads/2016/1112/6c315cfa8a70dfcbdb4641f21e6875ab.jpg",
//        "price": "3320",
//        "gysid": "14",
//        "group": "30146",
//        "im": ""
//    }

    public String channelname;
    public String webid;

    @SerializedName("gysid")
    public String aid;

    public String typeid;
    public String title;

    @SerializedName("txt")
    public String description;

    public String litpic;
    public String shownum;

    @SerializedName("group")
    public String kindlist;
    @SerializedName("im")
    public String attrid;

    public String headimgid;

    @SerializedName("id")
    public String tid;

    public String ishidden;

    public String price;

    public ForceEndSearchInfo(String channelname, String webid, String aid, String typeid, String title, String description, String litpic, String shownum, String kindlist, String attrid, String headimgid, String tid, String ishidden, String price) {
        this.channelname = channelname;
        this.webid = webid;
        this.aid = aid;
        this.typeid = typeid;
        this.title = title;
        this.description = description;
        this.litpic = litpic;
        this.shownum = shownum;
        this.kindlist = kindlist;
        this.attrid = attrid;
        this.headimgid = headimgid;
        this.tid = tid;
        this.ishidden = ishidden;
        this.price = price;
    }

    public ForceEndSearchInfo() {
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
    }

    public String getWebid() {
        return webid;
    }

    public void setWebid(String webid) {
        this.webid = webid;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getShownum() {
        return shownum;
    }

    public void setShownum(String shownum) {
        this.shownum = shownum;
    }

    public String getKindlist() {
        return kindlist;
    }

    public void setKindlist(String kindlist) {
        this.kindlist = kindlist;
    }

    public String getAttrid() {
        return attrid;
    }

    public void setAttrid(String attrid) {
        this.attrid = attrid;
    }

    public String getHeadimgid() {
        return headimgid;
    }

    public void setHeadimgid(String headimgid) {
        this.headimgid = headimgid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getIshidden() {
        return ishidden;
    }

    public void setIshidden(String ishidden) {
        this.ishidden = ishidden;
    }

    @Override
    public String toString() {
        return "ForceEndSearchInfo{" +
                "channelname='" + channelname + '\'' +
                ", webid='" + webid + '\'' +
                ", aid='" + aid + '\'' +
                ", typeid='" + typeid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", litpic='" + litpic + '\'' +
                ", shownum='" + shownum + '\'' +
                ", kindlist='" + kindlist + '\'' +
                ", attrid='" + attrid + '\'' +
                ", headimgid='" + headimgid + '\'' +
                ", tid='" + tid + '\'' +
                ", ishidden='" + ishidden + '\'' +
                '}';
    }
}
