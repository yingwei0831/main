package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/9.
 */
public class TrainTicketNumberCheckRequest extends BasicFetchModel {

    /**
     * FromStation : 北京
     * ArriveStation : 凌源
     * TravelTime : 2017-01-16
     * TrainCode : 2257
     */

    private String FromStation;
    private String ArriveStation;
    private String TravelTime;
    private String TrainCode;

    public String getFromStation() {
        return FromStation;
    }

    public void setFromStation(String FromStation) {
        this.FromStation = FromStation;
    }

    public String getArriveStation() {
        return ArriveStation;
    }

    public void setArriveStation(String ArriveStation) {
        this.ArriveStation = ArriveStation;
    }

    public String getTravelTime() {
        return TravelTime;
    }

    public void setTravelTime(String TravelTime) {
        this.TravelTime = TravelTime;
    }

    public String getTrainCode() {
        return TrainCode;
    }

    public void setTrainCode(String TrainCode) {
        this.TrainCode = TrainCode;
    }

    @Override
    public String toString() {
        return "TrainTicketNumberCheckRequest{" +
                "FromStation='" + FromStation + '\'' +
                ", ArriveStation='" + ArriveStation + '\'' +
                ", TravelTime='" + TravelTime + '\'' +
                ", TrainCode='" + TrainCode + '\'' +
                '}';
    }
}
