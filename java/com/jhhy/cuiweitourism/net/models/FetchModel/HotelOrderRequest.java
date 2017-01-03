package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/30.
 * 酒店下订单
 */
public class HotelOrderRequest extends BasicFetchModel {

    /**
     * uid : 52
     * HotelId : 90175394
     * HotelName : 北京东方威尼斯国际酒店
     * HotelAddress : 丰台区西三环南路23号东方威尼斯酒店(西三环西局出口南300米路西)
     * HotelCityCode : 0101
     * HotelCityName : 北京
     * RoomTypeId : 0001
     * RoomTypeName : 豪华大床房(无窗)
     * RatePlanId : 400934
     * RatePlanName : 含双早                   ProductID
     * ArrivalDate : 2016-12-15
     * DepartureDate : 2016-12-20
     * PassengerType : All                      必填；入住类型: All=统一价； Chinese =内宾价； OtherForeign =外宾价； HongKong =港澳台客人价 Japanese=日本客人价
     * PaymentType : SelfPay                    必填；付款方式: SelfPay-前台自付、Prepay-预付
     * NumberOfRooms : 1                        必填；房间数量，每一订单房间数量最好不要超过5间
     * NumberOfPassengers : 2                   旅客人数
     * EarliestArrivalTime : 2016-12-15 15:00:00    必填；最早到店时间
     * LatestArrivalTime : 2016-12-15 18:30:00      必填；最晚到店时间
     * CurrencyCode : RMB                       货币类型
     * TotalPrice : 299                         必填；总价，RatePlan 的 TotalPrice * 房间数；单位是元；房间单价可能是小数，总价也可能是带小数
     * PassengerIPAddress : 127.0.0.1           必填；客人访问IP
     * IsGuaranteeOrCharged : FALSE             必填；是否已担保或已付款，开通了公司担保业务的合作伙伴才能使用该属性
     * ConfirmationType : NotAllowedConfirm     必填；确认类型，NotAllowedConfirm 不允许确认(合作伙伴自查订单状 态后自行联系客人)_cn - 艺龙发短信给客人注：除了 NotAllowedConfirm，其余的选项艺龙都会发送短信，下单时如果输入了邮箱那么都会发送邮件
     * NoteToHotel : 测试订单，请忽略           非必填，给酒店备注，
     * （客人给酒店的备注，尽量不填写，以免影响房间确认速度，并可减少投诉；若必须要填，尽量对要求进行规范，便于沟通和处理。香港澳门的酒店此处备注不能包含："务必", "一定", "必须", "非要", "只能", "最晚", "百分之百", "不然就不去住"）
     * NoteToSuppler : 无需求                  非必填，给艺龙备注
     * rooms : [{"p":[{"Name":"cheng/feng","Mobile":"15210656933"},{"Name":"cheng/fengs","Mobile":"15210956933"}],"RoomNum":"1"}]
     * IsNeedInvoice : false                    是否需要发票 true 需要  false 不需要，默认 false
     * Contact : {"Name":"张天明","Mobile":"13859684587"}
     * DaysPrice : 299                          每日销售房价
     * DaysBasePrice : 299                      每日底价
     * DaysBreakfast : 含单早                   不为空，含早|单早
     * BasePrice : 299                          不为空，预订总底价
     * PlanType : 0                             不为空，产品协议类型，0 非协议，1 协议
     * TravelType : 1                           不为空，1 因公，2 因私
     * IsGuarantee : 0                          不为空，是否担保订单，0 否，1 是
     * imgurl : http://pavo.elongstatic.com/i/API350_350/e7424f6baf7c2e0c8996414d580c4983.jpg
     */

    private String uid;
    private String HotelId;
    private String HotelName;
    private String HotelAddress;
    private String HotelCityCode;
    private String HotelCityName;
    private String RoomTypeId;
    private String RoomTypeName;
    private String RatePlanId;
    private String RatePlanName;
    private String ArrivalDate;
    private String DepartureDate;
    private String PassengerType;
    private String PaymentType;
    private String NumberOfRooms;
    private String NumberOfPassengers;
    private String EarliestArrivalTime;
    private String LatestArrivalTime;
    private String CurrencyCode;
    private String TotalPrice;
    private String PassengerIPAddress;
    private String IsGuaranteeOrCharged;
    private String ConfirmationType;
    private String NoteToHotel;
    private String NoteToSuppler;
    private String IsNeedInvoice;
    private ContactBean Contact;
    private String DaysPrice;
    private String DaysBasePrice;
    private String DaysBreakfast;
    private String BasePrice;
    private String PlanType;
    private String TravelType;
    private String IsGuarantee;
    private String imgurl;
    private List<RoomBean> rooms;

    public HotelOrderRequest(String uid, String hotelId, String hotelName, String hotelAddress, String hotelCityCode, String hotelCityName, String roomTypeId, String roomTypeName, String ratePlanId, String ratePlanName, String arrivalDate, String departureDate, String passengerType, String paymentType, String numberOfRooms, String numberOfPassengers, String earliestArrivalTime, String latestArrivalTime, String currencyCode, String totalPrice, String passengerIPAddress, String isGuaranteeOrCharged, String confirmationType, String noteToHotel, String noteToSuppler, String isNeedInvoice, ContactBean contact, String daysPrice, String daysBasePrice, String daysBreakfast, String basePrice, String planType, String travelType, String isGuarantee, String imgurl) {
        this.uid = uid;
        HotelId = hotelId;
        HotelName = hotelName;
        HotelAddress = hotelAddress;
        HotelCityCode = hotelCityCode;
        HotelCityName = hotelCityName;
        RoomTypeId = roomTypeId;
        RoomTypeName = roomTypeName;
        RatePlanId = ratePlanId;
        RatePlanName = ratePlanName;
        ArrivalDate = arrivalDate;
        DepartureDate = departureDate;
        PassengerType = passengerType;
        PaymentType = paymentType;
        NumberOfRooms = numberOfRooms;
        NumberOfPassengers = numberOfPassengers;
        EarliestArrivalTime = earliestArrivalTime;
        LatestArrivalTime = latestArrivalTime;
        CurrencyCode = currencyCode;
        TotalPrice = totalPrice;
        PassengerIPAddress = passengerIPAddress;
        IsGuaranteeOrCharged = isGuaranteeOrCharged;
        ConfirmationType = confirmationType;
        NoteToHotel = noteToHotel;
        NoteToSuppler = noteToSuppler;
        IsNeedInvoice = isNeedInvoice;
        Contact = contact;
        DaysPrice = daysPrice;
        DaysBasePrice = daysBasePrice;
        DaysBreakfast = daysBreakfast;
        BasePrice = basePrice;
        PlanType = planType;
        TravelType = travelType;
        IsGuarantee = isGuarantee;
        this.imgurl = imgurl;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHotelId() {
        return HotelId;
    }

    public void setHotelId(String HotelId) {
        this.HotelId = HotelId;
    }

    public String getHotelName() {
        return HotelName;
    }

    public void setHotelName(String HotelName) {
        this.HotelName = HotelName;
    }

    public String getHotelAddress() {
        return HotelAddress;
    }

    public void setHotelAddress(String HotelAddress) {
        this.HotelAddress = HotelAddress;
    }

    public String getHotelCityCode() {
        return HotelCityCode;
    }

    public void setHotelCityCode(String HotelCityCode) {
        this.HotelCityCode = HotelCityCode;
    }

    public String getHotelCityName() {
        return HotelCityName;
    }

    public void setHotelCityName(String HotelCityName) {
        this.HotelCityName = HotelCityName;
    }

    public String getRoomTypeId() {
        return RoomTypeId;
    }

    public void setRoomTypeId(String RoomTypeId) {
        this.RoomTypeId = RoomTypeId;
    }

    public String getRoomTypeName() {
        return RoomTypeName;
    }

    public void setRoomTypeName(String RoomTypeName) {
        this.RoomTypeName = RoomTypeName;
    }

    public String getRatePlanId() {
        return RatePlanId;
    }

    public void setRatePlanId(String RatePlanId) {
        this.RatePlanId = RatePlanId;
    }

    public String getRatePlanName() {
        return RatePlanName;
    }

    public void setRatePlanName(String RatePlanName) {
        this.RatePlanName = RatePlanName;
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

    public String getPassengerType() {
        return PassengerType;
    }

    public void setPassengerType(String PassengerType) {
        this.PassengerType = PassengerType;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
    }

    public String getNumberOfRooms() {
        return NumberOfRooms;
    }

    public void setNumberOfRooms(String NumberOfRooms) {
        this.NumberOfRooms = NumberOfRooms;
    }

    public String getNumberOfPassengers() {
        return NumberOfPassengers;
    }

    public void setNumberOfPassengers(String NumberOfPassengers) {
        this.NumberOfPassengers = NumberOfPassengers;
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

    public String getCurrencyCode() {
        return CurrencyCode;
    }

    public void setCurrencyCode(String CurrencyCode) {
        this.CurrencyCode = CurrencyCode;
    }

    public String getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(String TotalPrice) {
        this.TotalPrice = TotalPrice;
    }

    public String getPassengerIPAddress() {
        return PassengerIPAddress;
    }

    public void setPassengerIPAddress(String PassengerIPAddress) {
        this.PassengerIPAddress = PassengerIPAddress;
    }

    public String getIsGuaranteeOrCharged() {
        return IsGuaranteeOrCharged;
    }

    public void setIsGuaranteeOrCharged(String IsGuaranteeOrCharged) {
        this.IsGuaranteeOrCharged = IsGuaranteeOrCharged;
    }

    public String getConfirmationType() {
        return ConfirmationType;
    }

    public void setConfirmationType(String ConfirmationType) {
        this.ConfirmationType = ConfirmationType;
    }

    public String getNoteToHotel() {
        return NoteToHotel;
    }

    public void setNoteToHotel(String NoteToHotel) {
        this.NoteToHotel = NoteToHotel;
    }

    public String getNoteToSuppler() {
        return NoteToSuppler;
    }

    public void setNoteToSuppler(String NoteToSuppler) {
        this.NoteToSuppler = NoteToSuppler;
    }

    public String getIsNeedInvoice() {
        return IsNeedInvoice;
    }

    public void setIsNeedInvoice(String IsNeedInvoice) {
        this.IsNeedInvoice = IsNeedInvoice;
    }

    public ContactBean getContact() {
        return Contact;
    }

    public void setContact(ContactBean Contact) {
        this.Contact = Contact;
    }

    public String getDaysPrice() {
        return DaysPrice;
    }

    public void setDaysPrice(String DaysPrice) {
        this.DaysPrice = DaysPrice;
    }

    public String getDaysBasePrice() {
        return DaysBasePrice;
    }

    public void setDaysBasePrice(String DaysBasePrice) {
        this.DaysBasePrice = DaysBasePrice;
    }

    public String getDaysBreakfast() {
        return DaysBreakfast;
    }

    public void setDaysBreakfast(String DaysBreakfast) {
        this.DaysBreakfast = DaysBreakfast;
    }

    public String getBasePrice() {
        return BasePrice;
    }

    public void setBasePrice(String BasePrice) {
        this.BasePrice = BasePrice;
    }

    public String getPlanType() {
        return PlanType;
    }

    public void setPlanType(String PlanType) {
        this.PlanType = PlanType;
    }

    public String getTravelType() {
        return TravelType;
    }

    public void setTravelType(String TravelType) {
        this.TravelType = TravelType;
    }

    public String getIsGuarantee() {
        return IsGuarantee;
    }

    public void setIsGuarantee(String IsGuarantee) {
        this.IsGuarantee = IsGuarantee;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public List<RoomBean> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomBean> rooms) {
        this.rooms = rooms;
    }

    public static class ContactBean  extends BasicFetchModel {
        /**
         * Name : 张天明
         * Mobile : 13859684587
         */

        private String Name;
        private String Mobile;

        public ContactBean(String name, String mobile) {
            Name = name;
            Mobile = mobile;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }
    }

    public static class RoomBean  extends BasicFetchModel {
        /**
         * p : [{"Name":"cheng/feng","Mobile":"15210656933"},{"Name":"cheng/fengs","Mobile":"15210956933"}]
         * RoomNum : 1
         */

        private String RoomNum;
        private List<PassengerBean> p;

        public String getRoomNum() {
            return RoomNum;
        }

        public void setRoomNum(String RoomNum) {
            this.RoomNum = RoomNum;
        }

        public List<PassengerBean> getP() {
            return p;
        }

        public void setP(List<PassengerBean> p) {
            this.p = p;
        }

        public static class PassengerBean  extends BasicFetchModel {
            /**
             * Name : cheng/feng
             * Mobile : 15210656933
             * Gender : 必填，Female 女，Maile 男, Unknown 保密
             */

            private String Name;
//            private String Gender;
            private String Mobile;

            public PassengerBean(String name, String mobile) {
                Name = name;
                Mobile = mobile;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }
        }
    }
}
