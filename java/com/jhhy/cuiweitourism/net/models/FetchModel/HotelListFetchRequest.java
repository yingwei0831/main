package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HotelListFetchRequest extends  BasicFetchModel {

//    "areaid": "8",
//    "starttime": "2016-09-16",
//    "endtime": "",
//    "keyword": "",
//    "page": "1",
//    "offset": "10",
//    "order": "price desc",
//    "price": "",
//    "level": ""

    public String areaid;
    public String starttime;
    public String endtime;
    public String keyword;
    public String page;
    public String offset;
    public String order;
    public String price;
    public String level;

    public HotelListFetchRequest(String areaid, String starttime, String endtime, String keyword, String page, String offset, String order, String price, String level) {
        this.areaid = areaid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.keyword = keyword;
        this.page = page;
        this.offset = offset;
        this.order = order;
        this.price = price;
        this.level = level;
    }

    public HotelListFetchRequest() {
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
