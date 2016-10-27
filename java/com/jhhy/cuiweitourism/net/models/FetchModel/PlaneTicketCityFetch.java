package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-24.
 */
public class PlaneTicketCityFetch extends BasicFetchModel {

    public String isdomc;

    public PlaneTicketCityFetch(String isdomc) {
        this.isdomc = isdomc;
    }

    public String getIsdomc() {
        return isdomc;
    }

    public void setIsdomc(String isdomc) {
        this.isdomc = isdomc;
    }
}
