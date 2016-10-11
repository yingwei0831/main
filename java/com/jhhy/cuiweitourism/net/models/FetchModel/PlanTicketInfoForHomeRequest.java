package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-10.
 */
public class PlanTicketInfoForHomeRequest extends BasicFetchModel {
//    {
//        "startcity": "PEK",
//            "endcity": "DLC",
//            "flydate": "2016-10-17"
//    }

    public String startcity;
    public String endcity;
    public String flydate;

    public PlanTicketInfoForHomeRequest(String startcity, String endcity, String flydate) {
        this.startcity = startcity;
        this.endcity = endcity;
        this.flydate = flydate;
    }

    public PlanTicketInfoForHomeRequest() {
    }

    public String getStartcity() {
        return startcity;
    }

    public void setStartcity(String startcity) {
        this.startcity = startcity;
    }

    public String getEndcity() {
        return endcity;
    }

    public void setEndcity(String endcity) {
        this.endcity = endcity;
    }

    public String getFlydate() {
        return flydate;
    }

    public void setFlydate(String flydate) {
        this.flydate = flydate;
    }
}
