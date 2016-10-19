package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HotelDetailInfo {
//    "title": "九寨沟喜来登大酒店",
//    "piclist": [
//                "http://www.cwly1118.com/uploads/2016/0607/573731e76d913a5c3f1c58eaea3f3d18.jpg",
//                "http://www.cwly1118.com/uploads/2016/0715/316c62e7521cb1bf4c67ea1ff1560841.jpg",
//                "http://www.cwly1118.com/uploads/2016/0715/b70f7dc92e81780c9b6dccbe50658a05.jpg",
//                "http://www.cwly1118.com/uploads/2016/0607/0f59a6280f041ee9a4493358160f7cb6.jpg"
//                ],
//    "id": "4",
//    "address": "北京市昌平区回龙观东大街地铁站c口",
//    "lng": "116.393345",
//    "lat": "39.940166",
//    "opentime": "2016-08-30",
//    "decoratetime": "2016-08-30",
//    "content": "滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴答滴",
//    "fuwu": "免费停车，免费洗衣，免费wifi",
//    "room": [
//            {
//                "roomid": "4",
//                "roomname": "标间",
//                "breakfirst": "含",
//                "roomstyle": "单床1.5米",
//                "imgs": [
//                        "http://www.cwly1118.com/uploads/main/litimg/20160608/20160608093452.jpg",
//                        "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173032.jpg",
//                        "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173037.jpg"
//                        ],
//                "price": "260"
//            },
//            {
//                "roomid": "5",
//                 "roomname": "情侣套房",
//                 "breakfirst": "三餐",
//                 "roomstyle": "大床1.8米",
//                 "imgs": [
//                          "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173904.jpg",
//                          "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173908.jpg",
//                          "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173910.jpg",
//                          "http://www.cwly1118.com/uploads/main/litimg/20160910/20160910173913.jpg"
//                          ],
//                "price": "800"
//            }
//    ]

    static public class Room{
        public String roomid;
        public String roomname;
        public String breakfirst;
        public String roomstyle;
        public ArrayList<String> imgs;
        public String price;

        public Room(String roomid, String roomname, String breakfirst, String roomstyle, String price, ArrayList<String> imgs) {
            this.roomid = roomid;
            this.roomname = roomname;
            this.breakfirst = breakfirst;
            this.roomstyle = roomstyle;
            this.price = price;
            this.imgs = imgs;
        }

        public String getBreakfirst() {
            return breakfirst;
        }

        public void setBreakfirst(String breakfirst) {
            this.breakfirst = breakfirst;
        }

        public String getRoomstyle() {
            return roomstyle;
        }

        public void setRoomstyle(String roomstyle) {
            this.roomstyle = roomstyle;
        }

        public Room() {
        }

        public String getRoomid() {
            return roomid;
        }

        public void setRoomid(String roomid) {
            this.roomid = roomid;
        }

        public String getRoomname() {
            return roomname;
        }

        public void setRoomname(String roomname) {
            this.roomname = roomname;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public ArrayList<String> getImgs() {
            return imgs;
        }

        public void setImgs(ArrayList<String> imgs) {
            this.imgs = imgs;
        }

        @Override
        public String toString() {
            return "Room{" +
                    "roomid='" + roomid + '\'' +
                    ", roomname='" + roomname + '\'' +
                    ", price='" + price + '\'' +
                    ", imgs=" + imgs +
                    '}';
        }
    }

    public String title;
    public ArrayList<String > piclist;
    public String id;
    public String address;
    public String lng;
    public String lat;
    public String opentime;
    public String decoratetime;
    public String content;
    public String fuwu;
    public ArrayList<Room> room;

    public HotelDetailInfo(String title, ArrayList<String> piclist, String id, String address, String lng, String lat, String opentime, String decoratetime, String content, String fuwu, ArrayList<Room> room) {
        this.title = title;
        this.piclist = piclist;
        this.id = id;
        this.address = address;
        this.lng = lng;
        this.lat = lat;
        this.opentime = opentime;
        this.decoratetime = decoratetime;
        this.content = content;
        this.fuwu = fuwu;
        this.room = room;
    }

    public HotelDetailInfo() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPiclist() {
        return piclist;
    }

    public void setPiclist(ArrayList<String> piclist) {
        this.piclist = piclist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Room> getRoom() {
        return room;
    }

    public void setRoom(ArrayList<Room> room) {
        this.room = room;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getOpentime() {
        return opentime;
    }

    public void setOpentime(String opentime) {
        this.opentime = opentime;
    }

    public String getDecoratetime() {
        return decoratetime;
    }

    public void setDecoratetime(String decoratetime) {
        this.decoratetime = decoratetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFuwu() {
        return fuwu;
    }

    public void setFuwu(String fuwu) {
        this.fuwu = fuwu;
    }

    @Override
    public String toString() {
        return "HotelDetailInfo{" +
                "title='" + title + '\'' +
                ", piclist=" + piclist +
                ", id='" + id + '\'' +
                ", address='" + address + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", opentime='" + opentime + '\'' +
                ", decoratetime='" + decoratetime + '\'' +
                ", content='" + content + '\'' +
                ", fuwu='" + fuwu + '\'' +
                ", room=" + room +
                '}';
    }
}
