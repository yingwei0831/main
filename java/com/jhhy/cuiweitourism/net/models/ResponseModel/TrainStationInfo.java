package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/11.
 */
public class TrainStationInfo {

    public ArrayList<ArrayList<String>> stations;

    public TrainStationInfo(ArrayList<ArrayList<String>> stations) {
        this.stations = stations;
    }

    public TrainStationInfo() {
    }

    public ArrayList<ArrayList<String>> getStations() {
        return stations;
    }

    public void setStations(ArrayList<ArrayList<String>> stations) {
        this.stations = stations;
    }

    @Override
    public String toString() {
        return "TrainStationInfo{" +
                "stations=" + stations +
                '}';
    }
}
