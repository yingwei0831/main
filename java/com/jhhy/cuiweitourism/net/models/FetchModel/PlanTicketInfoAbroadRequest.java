package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney on 2016-10-11.
 */
public class PlanTicketInfoAbroadRequest extends BasicFetchModel {

//    {
//        "traveltype": "RT",
//            "boardpoint": "PEK",
//            "offPoint": "BKK",
//            "departuredate": "2016-10-17",
//            "returndate": "2016-11-17",
//            "stoptype": "A",
//            "carrier": ""
//    }


    public String traveltype;
    public String boardpoint;
    public String offPoint;
    public String departuredate;
    public String returndate;
    public String stoptype;
    public String carrier;


    public PlanTicketInfoAbroadRequest(String traveltype, String boardpoint, String offPoint, String departuredate, String returndate, String stoptype, String carrier) {
        this.traveltype = traveltype;
        this.boardpoint = boardpoint;
        this.offPoint = offPoint;
        this.departuredate = departuredate;
        this.returndate = returndate;
        this.stoptype = stoptype;
        this.carrier = carrier;
    }

    public PlanTicketInfoAbroadRequest() {
    }

    public String getTraveltype() {
        return traveltype;
    }

    public void setTraveltype(String traveltype) {
        this.traveltype = traveltype;
    }

    public String getBoardpoint() {
        return boardpoint;
    }

    public void setBoardpoint(String boardpoint) {
        this.boardpoint = boardpoint;
    }

    public String getOffPoint() {
        return offPoint;
    }

    public void setOffPoint(String offPoint) {
        this.offPoint = offPoint;
    }

    public String getDeparturedate() {
        return departuredate;
    }

    public void setDeparturedate(String departuredate) {
        this.departuredate = departuredate;
    }

    public String getReturndate() {
        return returndate;
    }

    public void setReturndate(String returndate) {
        this.returndate = returndate;
    }

    public String getStoptype() {
        return stoptype;
    }

    public void setStoptype(String stoptype) {
        this.stoptype = stoptype;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
