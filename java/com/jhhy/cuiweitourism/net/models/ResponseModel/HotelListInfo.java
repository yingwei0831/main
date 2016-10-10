package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HotelListInfo {

//    "id": "3",
//    "title": "北京山河国际大酒店",
//    "address": "北京市昌平区",
//    "price": "999",
//    "litpic": "http://www.cwly1118.com/uploads/2015/0923/298c188644bc41b60f00e86528b3f69a.jpg",
//    "lng": "116.453137",
//    "lat": "39.949459",
//    "hotelrankid": "5",
//    "types": "五星级酒店"

    public  String id;
    public  String title;
    public  String address;
    public  String price;
    public  String litpic;
    public  String lng;
    public  String lat;
    public  String hotelrankid;
    public  String types;

    public HotelListInfo(String id, String title, String address, String price, String litpic, String lng, String lat, String hotelrankid, String types) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.litpic = litpic;
        this.lng = lng;
        this.lat = lat;
        this.hotelrankid = hotelrankid;
        this.types = types;
    }

    public HotelListInfo() {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
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

    public String getHotelrankid() {
        return hotelrankid;
    }

    public void setHotelrankid(String hotelrankid) {
        this.hotelrankid = hotelrankid;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "HotelListInfo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", address='" + address + '\'' +
                ", price='" + price + '\'' +
                ", litpic='" + litpic + '\'' +
                ", lng='" + lng + '\'' +
                ", lat='" + lat + '\'' +
                ", hotelrankid='" + hotelrankid + '\'' +
                ", types='" + types + '\'' +
                '}';
    }
}
