package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/30.
 * 担保规则请求
 */
public class HotelGuaranteeRulesRequest extends BasicFetchModel{

    /**
     * HotelID : 90702202
     * RoomTypeId : 0001
     * RatePlanId : 809404
     * ArrivalDate : 2016-12-30
     * DepartureDate : 2016-12-31
     * EarliestArrivalTime : 2016-12-30 18:00:00
     * LatestArrivalTime : 2016-12-31 18:00:00
     * NumberOfRooms : 1
     */

    private String HotelID;
    private String RoomTypeId;
    private String RatePlanId;
    private String ArrivalDate;
    private String DepartureDate;
    private String EarliestArrivalTime;
    private String LatestArrivalTime;
    private String NumberOfRooms;

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
    }

    public String getRoomTypeId() {
        return RoomTypeId;
    }

    public void setRoomTypeId(String RoomTypeId) {
        this.RoomTypeId = RoomTypeId;
    }

    public String getRatePlanId() {
        return RatePlanId;
    }

    public void setRatePlanId(String RatePlanId) {
        this.RatePlanId = RatePlanId;
    }

    public String getArrivalDate() {
        return ArrivalDate;
    }

    public void setArrivalDate(String ArrivalDate) {
        this.ArrivalDate = ArrivalDate;
    }

    public String getDepartureDate() {
        return DepartureDate;
    }

    public void setDepartureDate(String DepartureDate) {
        this.DepartureDate = DepartureDate;
    }

    public String getEarliestArrivalTime() {
        return EarliestArrivalTime;
    }

    public void setEarliestArrivalTime(String EarliestArrivalTime) {
        this.EarliestArrivalTime = EarliestArrivalTime;
    }

    public String getLatestArrivalTime() {
        return LatestArrivalTime;
    }

    public void setLatestArrivalTime(String LatestArrivalTime) {
        this.LatestArrivalTime = LatestArrivalTime;
    }

    public String getNumberOfRooms() {
        return NumberOfRooms;
    }

    public void setNumberOfRooms(String NumberOfRooms) {
        this.NumberOfRooms = NumberOfRooms;
    }
}
