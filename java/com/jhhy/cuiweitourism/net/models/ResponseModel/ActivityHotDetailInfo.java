package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by birney on 2016-10-10.
 */
public class ActivityHotDetailInfo implements Serializable{


//    "id": "1",
//    "title": "撒哈拉沙漠",
//    "price": "50000.00",
//    "num": "15",
//    "days": "5",
//    "cftime": "1472515200",
//    "cfcity": "20",
//    "feeinclude": "1.往返飞机票
//              2.餐饮
//              3.保险
//              4.导游
//              5.住宿",
//    "features": "撒哈拉沙漠约形成于250万年前，是世界第二大荒漠（仅次于南极洲），也是世界最大的沙质荒漠。它位于非洲北部，该地区气候条件非常恶劣，是地球上最不适合生物生存的地方之一。其总面积约容得下整个美国本土。“撒哈拉”是阿拉伯语的音译，在阿拉伯语中“撒哈拉”为大沙漠，源自当地游牧民族图阿雷格人的语言，原意即为“大荒漠”。",
//    "xlxq": "位于阿特拉斯山脉和地中海以南(约北纬35°线),约北纬14°线(250毫米等雨量线)以北。
//            撒哈拉沙漠西从大西洋沿岸开始，北部以阿特拉斯山脉和地中海为界，东部直抵红海，南部到达苏丹和尼日尔河河谷交界的萨赫勒——一个半沙漠乾草原的过渡区。
//            撒哈拉沙漠是世界最大的沙漠，几乎占满非洲北部全部，占全洲总面积的25%。沙漠东西约长4,800公里(3000英里)，南北在1300公里至1900公里(800至1200英里)之间，总面积约9065000平方公里。
//            横贯非洲大陆北部,东西长达5600公里,南北宽约1600公里，约占非洲总面积32%",
//    "xcinfo": "第一天：从北京出发
//              第二天：到达目的地安排住宿
//              第三天：开始进军沙漠
//              第四天:进军沙漠
//              第五天：返回北京",
//    "piclist": [
//            "http://www.cwly1118.com/uploads/2015/0909/6576cb3bfd961a0e13e6cc203b4ce012.jpg"
//            ],
//    "comment": {
//        "content": "这个活动很好",
//        "pllist": [
//                  "http://www.cwly1118.com/uploads/pinglun/2016/0905/e5fd08e12911719eb8cee0983dc55392.jpg",
//                  "http://www.cwly1118.com/uploads/pinglun/2016/0905"
//        ],
//        "nickname": "小蜗",
//        "face": "http://www.cwly1118.com/uploads/member/11474192836.jpg",
//        "addtime": null
//    },
//    "ctnum": "1"

    public String id;
    public String title;
    public String price;
    public String num;
    public String days;
    public String cftime;
    public String cfcity;
    public String feeinclude;
    public String  features;
    public ArrayList<String> piclist;

    public ActivityComment comment;

    public String ctnum;
    public String xlxq;
    public String xcinfo;

    public ActivityHotDetailInfo(String id, String title, String price, String num, String days, String cftime, String cfcity, String feeinclude, String features, ArrayList<String> piclist, ActivityComment comment, String ctnum, String xlxq, String xcinfo) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.num = num;
        this.days = days;
        this.cftime = cftime;
        this.cfcity = cfcity;
        this.feeinclude = feeinclude;
        this.features = features;
        this.piclist = piclist;
        this.comment = comment;
        this.ctnum = ctnum;
        this.xlxq = xlxq;
        this.xcinfo = xcinfo;
    }

    public ActivityHotDetailInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getCftime() {
        return cftime;
    }

    public void setCftime(String cftime) {
        this.cftime = cftime;
    }

    public String getCfcity() {
        return cfcity;
    }

    public void setCfcity(String cfcity) {
        this.cfcity = cfcity;
    }

    public String getFeeinclude() {
        return feeinclude;
    }

    public void setFeeinclude(String feeinclude) {
        this.feeinclude = feeinclude;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public ArrayList<String> getPiclist() {
        return piclist;
    }

    public void setPiclist(ArrayList<String> piclist) {
        this.piclist = piclist;
    }

    public ActivityComment getComment() {
        return comment;
    }

    public void setComment(ActivityComment comment) {
        this.comment = comment;
    }

    public String getCtnum() {
        return ctnum;
    }

    public void setCtnum(String ctnum) {
        this.ctnum = ctnum;
    }

    public String getXlxq() {
        return xlxq;
    }

    public void setXlxq(String xlxq) {
        this.xlxq = xlxq;
    }

    public String getXcinfo() {
        return xcinfo;
    }

    public void setXcinfo(String xcinfo) {
        this.xcinfo = xcinfo;
    }

    @Override
    public String toString() {
        return "ActivityHotDetailInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", price='" + price + '\'' +
                ", num='" + num + '\'' +
                ", days='" + days + '\'' +
                ", cftime='" + cftime + '\'' +
                ", cfcity='" + cfcity + '\'' +
                ", feeinclude='" + feeinclude + '\'' +
                ", features='" + features + '\'' +
                ", piclist=" + piclist +
                ", comment=" + comment.toString() +
                ", ctnum='" + ctnum + '\'' +
                ", xlxq='" + xlxq + '\'' +
                ", xcinfo='" + xcinfo + '\'' +
                '}';
    }
}
