package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/6.
 * 火车票订单详情
 */
public class TrainTicketOrderDetailResponse {

    /**
     * ErrorCode : 0
     * ErrorMsg :
     * OrderNo : 7010611580085019
     * PlatOrderNo : EXHC170106115850386
     * PlatCode : ES
     * Status : 已出票
     * SettlePrice : 1.00
     * PayTime : 2017-01-06 11:58:30
     * TktTime : 2017-01-06 12:00:33
     * TrainOrderNo : EA01699931
     * TBookTime : 2017-01-06 11:58:30
     * OrderType : N
     * TktSumPrice : 1.00
     * CounterSumFee : 0.00
     * InsSumPrice : 0.00
     * TradeNo : 17010611585172034453
     * DifferencePrice : 0.00
     * ActualPrice : 1.00
     * TicketInfo : {"FromStation":"新青","ToStation":"红星","TrainCode":"6274","TrainDate":"2017-01-11","FromTime":"06:17","ToDate":"2017-01-11","ToTime":"06:31","SeatType":"硬座","TrainType":"","Passengers":{"Passenger":[{"IsBX":"0","InsPrice":"0","InsCount":"0","PsgName":"李玉生","CardType":"2","CardNo":"211382198908302912","TicketType":"0","TicketPrice":"1.00","SeatType":"8","PsgNo":"1","PsgStatus":"T","RefundAmount":"0.00","TrainBox":"02","SeatNo":"001号"}]}}
     */

    private String ErrorCode;
    private String ErrorMsg;
    private String OrderNo;
    private String PlatOrderNo;
    private String PlatCode;
    private String Status;
    private String SettlePrice;
    private String PayTime;
    private String TktTime;
    private String TrainOrderNo;
    private String TBookTime;
    private String OrderType;
    private String TktSumPrice;
    private String CounterSumFee;
    private String InsSumPrice;
    private String TradeNo;
    private String DifferencePrice;
    private String ActualPrice;
    private TicketInfoBean TicketInfo;

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String ErrorCode) {
        this.ErrorCode = ErrorCode;
    }

    public String getErrorMsg() {
        return ErrorMsg;
    }

    public void setErrorMsg(String ErrorMsg) {
        this.ErrorMsg = ErrorMsg;
    }

    public String getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(String OrderNo) {
        this.OrderNo = OrderNo;
    }

    public String getPlatOrderNo() {
        return PlatOrderNo;
    }

    public void setPlatOrderNo(String PlatOrderNo) {
        this.PlatOrderNo = PlatOrderNo;
    }

    public String getPlatCode() {
        return PlatCode;
    }

    public void setPlatCode(String PlatCode) {
        this.PlatCode = PlatCode;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public String getSettlePrice() {
        return SettlePrice;
    }

    public void setSettlePrice(String SettlePrice) {
        this.SettlePrice = SettlePrice;
    }

    public String getPayTime() {
        return PayTime;
    }

    public void setPayTime(String PayTime) {
        this.PayTime = PayTime;
    }

    public String getTktTime() {
        return TktTime;
    }

    public void setTktTime(String TktTime) {
        this.TktTime = TktTime;
    }

    public String getTrainOrderNo() {
        return TrainOrderNo;
    }

    public void setTrainOrderNo(String TrainOrderNo) {
        this.TrainOrderNo = TrainOrderNo;
    }

    public String getTBookTime() {
        return TBookTime;
    }

    public void setTBookTime(String TBookTime) {
        this.TBookTime = TBookTime;
    }

    public String getOrderType() {
        return OrderType;
    }

    public void setOrderType(String OrderType) {
        this.OrderType = OrderType;
    }

    public String getTktSumPrice() {
        return TktSumPrice;
    }

    public void setTktSumPrice(String TktSumPrice) {
        this.TktSumPrice = TktSumPrice;
    }

    public String getCounterSumFee() {
        return CounterSumFee;
    }

    public void setCounterSumFee(String CounterSumFee) {
        this.CounterSumFee = CounterSumFee;
    }

    public String getInsSumPrice() {
        return InsSumPrice;
    }

    public void setInsSumPrice(String InsSumPrice) {
        this.InsSumPrice = InsSumPrice;
    }

    public String getTradeNo() {
        return TradeNo;
    }

    public void setTradeNo(String TradeNo) {
        this.TradeNo = TradeNo;
    }

    public String getDifferencePrice() {
        return DifferencePrice;
    }

    public void setDifferencePrice(String DifferencePrice) {
        this.DifferencePrice = DifferencePrice;
    }

    public String getActualPrice() {
        return ActualPrice;
    }

    public void setActualPrice(String ActualPrice) {
        this.ActualPrice = ActualPrice;
    }

    public TicketInfoBean getTicketInfo() {
        return TicketInfo;
    }

    public void setTicketInfo(TicketInfoBean TicketInfo) {
        this.TicketInfo = TicketInfo;
    }

    public static class TicketInfoBean {
        /**
         * FromStation : 新青
         * ToStation : 红星
         * TrainCode : 6274
         * TrainDate : 2017-01-11
         * FromTime : 06:17
         * ToDate : 2017-01-11
         * ToTime : 06:31
         * SeatType : 硬座
         * TrainType :
         * Passengers : {"Passenger":[{"IsBX":"0","InsPrice":"0","InsCount":"0","PsgName":"李玉生","CardType":"2","CardNo":"211382198908302912","TicketType":"0","TicketPrice":"1.00","SeatType":"8","PsgNo":"1","PsgStatus":"T","RefundAmount":"0.00","TrainBox":"02","SeatNo":"001号"}]}
         */

        private String FromStation;
        private String ToStation;
        private String TrainCode;
        private String TrainDate;
        private String FromTime;
        private String ToDate;
        private String ToTime;
        private String SeatType;
        private String TrainType;
        private PassengersBean Passengers;

        public String getFromStation() {
            return FromStation;
        }

        public void setFromStation(String FromStation) {
            this.FromStation = FromStation;
        }

        public String getToStation() {
            return ToStation;
        }

        public void setToStation(String ToStation) {
            this.ToStation = ToStation;
        }

        public String getTrainCode() {
            return TrainCode;
        }

        public void setTrainCode(String TrainCode) {
            this.TrainCode = TrainCode;
        }

        public String getTrainDate() {
            return TrainDate;
        }

        public void setTrainDate(String TrainDate) {
            this.TrainDate = TrainDate;
        }

        public String getFromTime() {
            return FromTime;
        }

        public void setFromTime(String FromTime) {
            this.FromTime = FromTime;
        }

        public String getToDate() {
            return ToDate;
        }

        public void setToDate(String ToDate) {
            this.ToDate = ToDate;
        }

        public String getToTime() {
            return ToTime;
        }

        public void setToTime(String ToTime) {
            this.ToTime = ToTime;
        }

        public String getSeatType() {
            return SeatType;
        }

        public void setSeatType(String SeatType) {
            this.SeatType = SeatType;
        }

        public String getTrainType() {
            return TrainType;
        }

        public void setTrainType(String TrainType) {
            this.TrainType = TrainType;
        }

        public PassengersBean getPassengers() {
            return Passengers;
        }

        public void setPassengers(PassengersBean Passengers) {
            this.Passengers = Passengers;
        }

        @Override
        public String toString() {
            return "TicketInfoBean{" +
                    "FromStation='" + FromStation + '\'' +
                    ", ToStation='" + ToStation + '\'' +
                    ", TrainCode='" + TrainCode + '\'' +
                    ", TrainDate='" + TrainDate + '\'' +
                    ", FromTime='" + FromTime + '\'' +
                    ", ToDate='" + ToDate + '\'' +
                    ", ToTime='" + ToTime + '\'' +
                    ", SeatType='" + SeatType + '\'' +
                    ", TrainType='" + TrainType + '\'' +
                    ", Passengers=" + Passengers +
                    '}';
        }
    }

    public static class PassengersBean {
        private List<PassengerBean> Passenger;

        public List<PassengerBean> getPassenger() {
            return Passenger;
        }

        public void setPassenger(List<PassengerBean> Passenger) {
            this.Passenger = Passenger;
        }

        @Override
        public String toString() {
            return "PassengersBean{" +
                    "Passenger=" + Passenger +
                    '}';
        }
    }

    public static class PassengerBean {
        /**
         * IsBX : 0
         * InsPrice : 0
         * InsCount : 0
         * PsgName : 李玉生
         * CardType : 2
         * CardNo : 211382198908302912
         * TicketType : 0
         * TicketPrice : 1.00
         * SeatType : 8
         * PsgNo : 1
         * PsgStatus : T
         * RefundAmount : 0.00
         * TrainBox : 02
         * SeatNo : 001号
         */

        private String IsBX;
        private String InsPrice;
        private String InsCount;
        private String PsgName;
        private String CardType;
        private String CardNo;
        private String TicketType;
        private String TicketPrice;
        private String SeatType;
        private String PsgNo;
        private String PsgStatus;
        private String RefundAmount;
        private String TrainBox;
        private String SeatNo;

        public String getIsBX() {
            return IsBX;
        }

        public void setIsBX(String IsBX) {
            this.IsBX = IsBX;
        }

        public String getInsPrice() {
            return InsPrice;
        }

        public void setInsPrice(String InsPrice) {
            this.InsPrice = InsPrice;
        }

        public String getInsCount() {
            return InsCount;
        }

        public void setInsCount(String InsCount) {
            this.InsCount = InsCount;
        }

        public String getPsgName() {
            return PsgName;
        }

        public void setPsgName(String PsgName) {
            this.PsgName = PsgName;
        }

        public String getCardType() {
            return CardType;
        }

        public void setCardType(String CardType) {
            this.CardType = CardType;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String CardNo) {
            this.CardNo = CardNo;
        }

        public String getTicketType() {
            return TicketType;
        }

        public void setTicketType(String TicketType) {
            this.TicketType = TicketType;
        }

        public String getTicketPrice() {
            return TicketPrice;
        }

        public void setTicketPrice(String TicketPrice) {
            this.TicketPrice = TicketPrice;
        }

        public String getSeatType() {
            return SeatType;
        }

        public void setSeatType(String SeatType) {
            this.SeatType = SeatType;
        }

        public String getPsgNo() {
            return PsgNo;
        }

        public void setPsgNo(String PsgNo) {
            this.PsgNo = PsgNo;
        }

        public String getPsgStatus() {
            return PsgStatus;
        }

        public void setPsgStatus(String PsgStatus) {
            this.PsgStatus = PsgStatus;
        }

        public String getRefundAmount() {
            return RefundAmount;
        }

        public void setRefundAmount(String RefundAmount) {
            this.RefundAmount = RefundAmount;
        }

        public String getTrainBox() {
            return TrainBox;
        }

        public void setTrainBox(String TrainBox) {
            this.TrainBox = TrainBox;
        }

        public String getSeatNo() {
            return SeatNo;
        }

        public void setSeatNo(String SeatNo) {
            this.SeatNo = SeatNo;
        }

        @Override
        public String toString() {
            return "PassengerBean{" +
                    "IsBX='" + IsBX + '\'' +
                    ", InsPrice='" + InsPrice + '\'' +
                    ", InsCount='" + InsCount + '\'' +
                    ", PsgName='" + PsgName + '\'' +
                    ", CardType='" + CardType + '\'' +
                    ", CardNo='" + CardNo + '\'' +
                    ", TicketType='" + TicketType + '\'' +
                    ", TicketPrice='" + TicketPrice + '\'' +
                    ", SeatType='" + SeatType + '\'' +
                    ", PsgNo='" + PsgNo + '\'' +
                    ", PsgStatus='" + PsgStatus + '\'' +
                    ", RefundAmount='" + RefundAmount + '\'' +
                    ", TrainBox='" + TrainBox + '\'' +
                    ", SeatNo='" + SeatNo + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TrainTicketOrderDetailResponse{" +
                "ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMsg='" + ErrorMsg + '\'' +
                ", OrderNo='" + OrderNo + '\'' +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", PlatCode='" + PlatCode + '\'' +
                ", Status='" + Status + '\'' +
                ", SettlePrice='" + SettlePrice + '\'' +
                ", PayTime='" + PayTime + '\'' +
                ", TktTime='" + TktTime + '\'' +
                ", TrainOrderNo='" + TrainOrderNo + '\'' +
                ", TBookTime='" + TBookTime + '\'' +
                ", OrderType='" + OrderType + '\'' +
                ", TktSumPrice='" + TktSumPrice + '\'' +
                ", CounterSumFee='" + CounterSumFee + '\'' +
                ", InsSumPrice='" + InsSumPrice + '\'' +
                ", TradeNo='" + TradeNo + '\'' +
                ", DifferencePrice='" + DifferencePrice + '\'' +
                ", ActualPrice='" + ActualPrice + '\'' +
                ", TicketInfo=" + TicketInfo +
                '}';
    }
}
