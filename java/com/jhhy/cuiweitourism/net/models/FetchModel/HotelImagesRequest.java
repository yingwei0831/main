package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/11.
 * 获取酒店图片
 */
public class HotelImagesRequest extends BasicFetchModel {

    /**
     * HotelID : 30101151
     * RoomID : 0016
     * Count : 0
     */

    private String HotelID;
    private String RoomID;
    private String Count;

    public HotelImagesRequest(String hotelID, String roomID, String count) {
        HotelID = hotelID;
        RoomID = roomID;
        Count = count;
    }

    public String getHotelID() {
        return HotelID;
    }

    public void setHotelID(String HotelID) {
        this.HotelID = HotelID;
    }

    public String getRoomID() {
        return RoomID;
    }

    public void setRoomID(String RoomID) {
        this.RoomID = RoomID;
    }

    public String getCount() {
        return Count;
    }

    public void setCount(String Count) {
        this.Count = Count;
    }
}
