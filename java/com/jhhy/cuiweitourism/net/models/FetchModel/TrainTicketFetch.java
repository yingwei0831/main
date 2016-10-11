package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/11.
 */
public class TrainTicketFetch extends BasicFetchModel {

//    {
//        "fromstation": "北京",
//            "arrivestation": "上海",
//            "traveltime": "2016-09-30",
//            "traincode": "",
//            "traintype": "",
//            "trainseattype": "",
//            "onlylowprice": ""
//    }

    public  String fromstation;
    public  String arrivestation;
    public  String traveltime;
    public  String traincode;
    public  String traintype;
    public  String trainseattype;
    public  String onlylowprice;


    public TrainTicketFetch(String fromstation, String arrivestation, String traveltime, String traincode, String traintype, String trainseattype, String onlylowprice) {
        this.fromstation = fromstation;
        this.arrivestation = arrivestation;
        this.traveltime = traveltime;
        this.traincode = traincode;
        this.traintype = traintype;
        this.trainseattype = trainseattype;
        this.onlylowprice = onlylowprice;
    }

    public TrainTicketFetch() {
    }

    public String getFromstation() {
        return fromstation;
    }

    public void setFromstation(String fromstation) {
        this.fromstation = fromstation;
    }

    public String getArrivestation() {
        return arrivestation;
    }

    public void setArrivestation(String arrivestation) {
        this.arrivestation = arrivestation;
    }

    public String getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(String traveltime) {
        this.traveltime = traveltime;
    }

    public String getTraincode() {
        return traincode;
    }

    public void setTraincode(String traincode) {
        this.traincode = traincode;
    }

    public String getTraintype() {
        return traintype;
    }

    public void setTraintype(String traintype) {
        this.traintype = traintype;
    }

    public String getTrainseattype() {
        return trainseattype;
    }

    public void setTrainseattype(String trainseattype) {
        this.trainseattype = trainseattype;
    }

    public String getOnlylowprice() {
        return onlylowprice;
    }

    public void setOnlylowprice(String onlylowprice) {
        this.onlylowprice = onlylowprice;
    }
}
