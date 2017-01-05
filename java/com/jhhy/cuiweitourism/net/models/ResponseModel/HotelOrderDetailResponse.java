package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/5.
 */
public class HotelOrderDetailResponse {

    /**
     * ErrorCode : 0
     * PlatOrderNo : 196308135
     * HotelId : 90735161
     * HotelName : 北京蜡笔小新连锁公寓(百环家园店女生公寓)
     * RoomTypeId : 0003
     * RoomTypeName : 八人间
     * RatePlanId : 876254
     * RatePlanName : 不含早
     * ArrivalDate : 2017/1/4 0:00:00
     * DepartureDate : 2017/1/5 0:00:00
     * Status : 11                          10 未确认，  11 已取消，  12 已确认，  13 入住中， 14 正常离店，  15 提前离店，  16NoShow
     * PaymentType : SelfPay
     * EarliestArrivalTime : 2017/1/4 19:00:00
     * LatestArrivalTime : 2017/1/4 23:59:00
     * CurrencyCode : RMB
     * TotalPrice : 60.00
     * ConfirmationType : []
     * CreationDate : 2017/1/4 18:33:00
     * CancelTime : 2017/1/4 18:53:00
     * Contact : {"Name":"aaaa","Email":[],"Mobile":"13260336699","Phone":"13260336699","Fax":[],"Gender":"Unknown"}
     * NightlyRates : {"NightlyRate":{"Date":"2017-01-04T00:00:00","Member":"30.00","Cost":"30.00"}}
     * Customers : {"Customer":[{"Name":"Lll","Email":[],"Mobile":"66666","Phone":"66666","Fax":[],"Gender":"Unknown","IdType":[],"IdNo":[],"Nationality":"中国"},{"Name":"Huh","Email":[],"Mobile":"4653865976","Phone":"4653865976","Fax":[],"Gender":"Unknown","IdType":[],"IdNo":[],"Nationality":"中国"}]}
     */

    private String orderSN;

    private String ErrorCode;
    private String PlatOrderNo;
    private String HotelId;
    private String HotelName;
    private String RoomTypeId;
    private String RoomTypeName;
    private String RatePlanId;
    private String RatePlanName;
    private String ArrivalDate;
    private String DepartureDate;
    private String Status;
    private String PaymentType;
    private String EarliestArrivalTime;
    private String LatestArrivalTime;
    private String CurrencyCode;
    private String TotalPrice;
    private String CreationDate;
//    private String CancelTime;
    private ContactBean Contact;
    private NightlyRatesBean NightlyRates;
    private CustomersBean Customers;
//    private String ConfirmationType;


    public String getOrderSN() {
        return orderSN;
    }

    public void setOrderSN(String orderSN) {
        this.orderSN = orderSN;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String PlatOrderNo) {
        this.PlatOrderNo = PlatOrderNo;
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

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String PaymentType) {
        this.PaymentType = PaymentType;
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

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String CreationDate) {
        this.CreationDate = CreationDate;
    }

//    public String getCancelTime() {
//        return CancelTime;
//    }

//    public void setCancelTime(String CancelTime) {
//        this.CancelTime = CancelTime;
//    }

    public ContactBean getContact() {
        return Contact;
    }

    public void setContact(ContactBean Contact) {
        this.Contact = Contact;
    }

    public NightlyRatesBean getNightlyRates() {
        return NightlyRates;
    }

    public void setNightlyRates(NightlyRatesBean NightlyRates) {
        this.NightlyRates = NightlyRates;
    }

    public CustomersBean getCustomers() {
        return Customers;
    }

    public void setCustomers(CustomersBean Customers) {
        this.Customers = Customers;
    }

//    public String getConfirmationType() {
//        return ConfirmationType;
//    }
//
//    public void setConfirmationType(String ConfirmationType) {
//        this.ConfirmationType = ConfirmationType;
//    }

    public static class ContactBean {
        /**联系人
         * Name : aaaa
         * Email : []
         * Mobile : 13260336699
         * Phone : 13260336699
         * Fax : []
         * Gender : Unknown
         */

        private String Name;
        private String Mobile;
//        private String Phone;
        private String Gender;
//        private List<?> Email;
//        private List<?> Fax;

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

//        public String getPhone() {
//            return Phone;
//        }

//        public void setPhone(String Phone) {
//            this.Phone = Phone;
//        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

//        public List<?> getEmail() {
//            return Email;
//        }
//
//        public void setEmail(List<?> Email) {
//            this.Email = Email;
//        }
//
//        public List<?> getFax() {
//            return Fax;
//        }
//
//        public void setFax(List<?> Fax) {
//            this.Fax = Fax;
//        }

        @Override
        public String toString() {
            return "ContactBean{" +
                    "Name='" + Name + '\'' +
                    ", Mobile='" + Mobile + '\'' +
                    ", Gender='" + Gender + '\'' +
                    '}';
        }
    }

    public static class NightlyRatesBean {
        /**
         * NightlyRate : {"Date":"2017-01-04T00:00:00","Member":"30.00","Cost":"30.00"}
         */

        private NightlyRateBean NightlyRate;

        public NightlyRateBean getNightlyRate() {
            return NightlyRate;
        }

        public void setNightlyRate(NightlyRateBean NightlyRate) {
            this.NightlyRate = NightlyRate;
        }

        @Override
        public String toString() {
            return "NightlyRatesBean{" +
                    "NightlyRate=" + NightlyRate +
                    '}';
        }
    }
    public static class NightlyRateBean {
        /**
         * Date : 2017-01-04T00:00:00
         * Member : 30.00
         * Cost : 30.00
         */

        private String Date;
        private String Member;
        private String Cost;

        public String getDate() {
            return Date;
        }

        public void setDate(String Date) {
            this.Date = Date;
        }

        public String getMember() {
            return Member;
        }

        public void setMember(String Member) {
            this.Member = Member;
        }

        public String getCost() {
            return Cost;
        }

        public void setCost(String Cost) {
            this.Cost = Cost;
        }

        @Override
        public String toString() {
            return "NightlyRateBean{" +
                    "Date='" + Date + '\'' +
                    ", Member='" + Member + '\'' +
                    ", Cost='" + Cost + '\'' +
                    '}';
        }
    }
    public static class CustomersBean {
        private List<CustomerBean> Customer;

        public List<CustomerBean> getCustomer() {
            return Customer;
        }

        public void setCustomer(List<CustomerBean> Customer) {
            this.Customer = Customer;
        }

        @Override
        public String toString() {
            return "CustomersBean{" +
                    "Customer=" + Customer +
                    '}';
        }
    }
    public static class CustomerBean {
        /**
         * Name : Lll
         * Email : []
         * Mobile : 66666
         * Phone : 66666
         * Fax : []
         * Gender : Unknown
         * IdType : []
         * IdNo : []
         * Nationality : 中国
         */

        private String Name;
        private String Mobile;
        private String Phone;
        private String Gender;
        private String Nationality;
//        private List<?> Email;
//        private List<?> Fax;
//        private List<?> IdType;
//        private List<?> IdNo;

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

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String Phone) {
            this.Phone = Phone;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getNationality() {
            return Nationality;
        }

        public void setNationality(String Nationality) {
            this.Nationality = Nationality;
        }

//        public List<?> getEmail() {
//            return Email;
//        }
//
//        public void setEmail(List<?> Email) {
//            this.Email = Email;
//        }
//
//        public List<?> getFax() {
//            return Fax;
//        }
//
//        public void setFax(List<?> Fax) {
//            this.Fax = Fax;
//        }
//
//        public List<?> getIdType() {
//            return IdType;
//        }
//
//        public void setIdType(List<?> IdType) {
//            this.IdType = IdType;
//        }
//
//        public List<?> getIdNo() {
//            return IdNo;
//        }
//
//        public void setIdNo(List<?> IdNo) {
//            this.IdNo = IdNo;
//        }


        @Override
        public String toString() {
            return "CustomerBean{" +
                    "Name='" + Name + '\'' +
                    ", Mobile='" + Mobile + '\'' +
                    ", Phone='" + Phone + '\'' +
                    ", Gender='" + Gender + '\'' +
                    ", Nationality='" + Nationality + '\'' +
                    '}';
        }

    }

    @Override
    public String toString() {
        return "HotelOrderDetailResponse{" +
                "orderSN='" + orderSN + '\'' +
                ", ErrorCode='" + ErrorCode + '\'' +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", HotelId='" + HotelId + '\'' +
                ", HotelName='" + HotelName + '\'' +
                ", RoomTypeId='" + RoomTypeId + '\'' +
                ", RoomTypeName='" + RoomTypeName + '\'' +
                ", RatePlanId='" + RatePlanId + '\'' +
                ", RatePlanName='" + RatePlanName + '\'' +
                ", ArrivalDate='" + ArrivalDate + '\'' +
                ", DepartureDate='" + DepartureDate + '\'' +
                ", Status='" + Status + '\'' +
                ", PaymentType='" + PaymentType + '\'' +
                ", EarliestArrivalTime='" + EarliestArrivalTime + '\'' +
                ", LatestArrivalTime='" + LatestArrivalTime + '\'' +
                ", CurrencyCode='" + CurrencyCode + '\'' +
                ", TotalPrice='" + TotalPrice + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                ", Contact=" + Contact +
                ", NightlyRates=" + NightlyRates +
                ", Customers=" + Customers +
                '}';
    }
}
