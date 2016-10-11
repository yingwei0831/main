package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class HotelDetailRequest extends BasicFetchModel {

    public String id;

    public HotelDetailRequest(String id) {
        this.id = id;
    }

    public HotelDetailRequest() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
