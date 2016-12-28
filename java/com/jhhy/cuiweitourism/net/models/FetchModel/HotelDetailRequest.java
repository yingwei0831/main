package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birneysky on 16/10/10.
 */
public class HotelDetailRequest extends BasicFetchModel {

    /**                          是否必填
     * HotelID : 90175394           Y
     * CheckInDate : 2016-12-13     Y
     * CheckOutDate : 2016-12-20    Y
     * RoomID : 0002                N
     * ProductID : 400931           N
     */

    private String HotelID;
    private String CheckInDate;
    private String CheckOutDate;
    private String RoomID;
    private String ProductID;

    public HotelDetailRequest(String hotelID, String checkInDate, String checkOutDate) {
        HotelID = hotelID;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
    }

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
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

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String ProductID) {
        this.ProductID = ProductID;
    }
}
