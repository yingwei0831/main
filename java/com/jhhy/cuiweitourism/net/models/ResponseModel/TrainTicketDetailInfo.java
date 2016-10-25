package com.jhhy.cuiweitourism.net.models.ResponseModel;

import android.app.SearchableInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by birney on 2016-10-11.
 */
public class TrainTicketDetailInfo implements Serializable{

    static public class SeatInfo implements Comparable<SeatInfo>, Serializable{
        public String seatType;//"座位类型",
        public String seatName;//"座位名称",
        public String seatCode;//"座位代码",
        public String seatCount;//"座位数量",
        public String floorPrice;//"最低价格",
        public String topBedPrice;//"上铺/座位价格",
        public String midBedprice;//"中铺价格",
        public String downBenPrice;//"下铺价格"

        @Override
        public String toString() {
            return "SeatInfo{" +
                    "seatType='" + seatType + '\'' +
                    ", seatName='" + seatName + '\'' +
                    ", seatCode='" + seatCode + '\'' +
                    ", seatCount='" + seatCount + '\'' +
                    ", floorPrice='" + floorPrice + '\'' +
                    ", topBedPrice='" + topBedPrice + '\'' +
                    ", midBedprice='" + midBedprice + '\'' +
                    ", downBenPrice='" + downBenPrice + '\'' +
                    '}';
        }

        @Override
        public int compareTo(SeatInfo o) {
            if (Float.parseFloat(this.floorPrice) > Float.parseFloat(o.floorPrice)){
                return 1;
            }else if (Float.parseFloat(this.floorPrice) < Float.parseFloat(o.floorPrice)){
                return -1;
            }
            return 0;
        }
    }


    public String trainNum;//"车次",
    public String trainTypeName; //"车次类型名称",
    public String trainTypeCode; //"车次类型代码",

    public String departureStation;//"出发站",
    public String departureDate;//"出发日期(yyyy-MM-dd)",
    public String departureTime;//"出发时间(HH:mm)",
    public String arrivalStation;//"到达站",
    public String arrivalDate;//"到达日期(yyyy-MM-dd)",
    public String arrivalTime;//"到达时间(HH:mm)",
    public String duration;//"历时(分钟)",
    public String startingStation;//"始发站",
    public String startingTime;//"始发时间(HH:mm)",
    public String terminus;//"终点站",
    public String terminalTime;//"终点时间(HH:mm)",
    public String numberOfRemainingSeats;//"剩余座位张数(不包含无座)",
    public ArrayList<SeatInfo> seatInfoArray = new ArrayList<SeatInfo>();


    @Override
    public String toString() {
        return "TrainTicketDetailInfo{" +
                "trainNum='" + trainNum + '\'' +
                ", trainTypeName='" + trainTypeName + '\'' +
                ", trainTypeCode='" + trainTypeCode + '\'' +
                ", departureStation='" + departureStation + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalStation='" + arrivalStation + '\'' +
                ", arrivalDate='" + arrivalDate + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", duration='" + duration + '\'' +
                ", startingStation='" + startingStation + '\'' +
                ", startingTime='" + startingTime + '\'' +
                ", terminus='" + terminus + '\'' +
                ", terminalTime='" + terminalTime + '\'' +
                ", numberOfRemainingSeats='" + numberOfRemainingSeats + '\'' +
                ", seatInfoArray=" + seatInfoArray +
                '}';
    }
}
