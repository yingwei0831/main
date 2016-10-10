package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-09.
 */
public class ActivityHot extends BasicFetchModel {
    //"areaid":"20","order":"addtime desc","day":"5",
    // "price":"2000,50000","zcfdate":"","page":"1","offset":"10"}
    public String areaid;
    public String order;
    public String day;
    public String price;
    public String zcfdate;
    public String page;
    public String offset;

    public ActivityHot(String areaid, String order, String day, String price, String zcfdate, String page, String offset) {
        this.areaid = areaid;
        this.order = order;
        this.day = day;
        this.price = price;
        this.zcfdate = zcfdate;
        this.page = page;
        this.offset = offset;
    }

    public ActivityHot() {
    }

    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getZcfdate() {
        return zcfdate;
    }

    public void setZcfdate(String zcfdate) {
        this.zcfdate = zcfdate;
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
}
