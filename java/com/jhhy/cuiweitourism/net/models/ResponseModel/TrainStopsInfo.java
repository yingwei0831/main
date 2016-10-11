package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/11.
 */
public class TrainStopsInfo {

    static public class Stop{
        public String arrtime;
        public String costtime;
        public String distance;
        public String interval;
        public String name;
        public String starttime;
        public String stationno;

        public Stop(String arrtime, String costtime, String distance, String interval, String name, String starttime, String stationno) {
            this.arrtime = arrtime;
            this.costtime = costtime;
            this.distance = distance;
            this.interval = interval;
            this.name = name;
            this.starttime = starttime;
            this.stationno = stationno;
        }

        public Stop() {
        }

        public String getArrtime() {
            return arrtime;
        }

        public void setArrtime(String arrtime) {
            this.arrtime = arrtime;
        }

        public String getCosttime() {
            return costtime;
        }

        public void setCosttime(String costtime) {
            this.costtime = costtime;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getStationno() {
            return stationno;
        }

        public void setStationno(String stationno) {
            this.stationno = stationno;
        }

        @Override
        public String toString() {
            return "Stop{" +
                    "arrtime='" + arrtime + '\'' +
                    ", costtime='" + costtime + '\'' +
                    ", distance='" + distance + '\'' +
                    ", interval='" + interval + '\'' +
                    ", name='" + name + '\'' +
                    ", starttime='" + starttime + '\'' +
                    ", stationno='" + stationno + '\'' +
                    '}';
        }
    }



    public ArrayList<Stop> stopsInfo;

    public TrainStopsInfo(ArrayList<Stop> stopsInfo) {
        this.stopsInfo = stopsInfo;
    }

    public TrainStopsInfo() {
    }

    public ArrayList<Stop> getStopsInfo() {
        return stopsInfo;
    }

    public void setStopsInfo(ArrayList<Stop> stopsInfo) {
        this.stopsInfo = stopsInfo;
    }

    @Override
    public String toString() {
        return "TrainStopsInfo{" +
                "stopsInfo=" + stopsInfo +
                '}';
    }
}
