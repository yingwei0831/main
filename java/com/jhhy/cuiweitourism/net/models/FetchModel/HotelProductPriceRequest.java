package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/11.
 * 酒店产品价格明细
 */
public class HotelProductPriceRequest extends BasicFetchModel {

    /**
     * HotelID : 90716304
     * CheckInDate : 2017-02-13
     * CheckOutDate : 2017-02-14
     * RoomID : 0002
     * ProductID : 3895288
     */

    private String HotelID;
    private String CheckInDate;
    private String CheckOutDate;
    private String RoomID;
    private String ProductID;

    public HotelProductPriceRequest(String hotelID, String checkInDate, String checkOutDate, String roomID, String productID) {
        HotelID = hotelID;
        CheckInDate = checkInDate;
        CheckOutDate = checkOutDate;
        RoomID = roomID;
        ProductID = productID;
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
