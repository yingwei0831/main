package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/29.
 * 数据校验
 */
public class HotelPriceCheckRequest extends BasicFetchModel {

    /**
     * CheckInDate : 2016-12-15
     * CheckOutDate : 2016-12-18
     * EarliestArrivalTime : 2016-12-15 15:00:00
     * LatestArrivalTime : 2016-12-15 18:00:00
     * HotelId : 40501037
     * RoomTypeId : 0007
     * RatePlanId : 102014      ProductId
     * TotalPrice : 840
     * NumberOfRooms : 2
     */

    private String CheckInDate;
    private String CheckOutDate;
    private String EarliestArrivalTime;
    private String LatestArrivalTime;
    private String HotelId;
    private String RoomTypeId;
    private String RatePlanId;
    private String TotalPrice;
    private String NumberOfRooms;

    public HotelPriceCheckRequest(String checkInDate, String checkOutDate, String earliestArrivalTime, String latestArrivalTime, String hotelId, String roomTypeId, String ratePlanId, String totalPrice, String numberOfRooms) {
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        EarliestArrivalTime = earliestArrivalTime;
        LatestArrivalTime = latestArrivalTime;
        HotelId = hotelId;
        RoomTypeId = roomTypeId;
        RatePlanId = ratePlanId;
        TotalPrice = totalPrice;
        NumberOfRooms = numberOfRooms;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String CheckInDate) {
        this.CheckInDate = CheckInDate;
    }

    public String getCheckOutDate() {
        return CheckOutDate;
    }

    public void setCheckOutDate(String CheckOutDate) {
        this.CheckOutDate = CheckOutDate;
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

    public String getHotelId() {
        return HotelId;
    }

    public void setHotelId(String HotelId) {
        this.HotelId = HotelId;
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

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getNumberOfRooms() {
        return NumberOfRooms;
    }

    public void setNumberOfRooms(String NumberOfRooms) {
        this.NumberOfRooms = NumberOfRooms;
    }
}
