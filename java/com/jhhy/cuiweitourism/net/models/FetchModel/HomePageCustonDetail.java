package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageCustonDetail extends BasicFetchModel {
    public String id;

    public HomePageCustonDetail() {
    }

    public HomePageCustonDetail(String id) {

        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
