package com.jhhy.cuiweitourism.net.models.ResponseModel;

import com.jhhy.cuiweitourism.net.models.FetchModel.PlaneTicketOrderInternationalRequest;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/12.
 */
public class PlaneTicketDetailInternationalResponse {

    /**
     * ordersn : 82985386740963
     * price : 1451.00
     * status : 1
     * addtime : 1484198538
     * linkman : 张三
     * linktel : 15210656911
     * title : 北京→阿比林
     * infos : {"uid":"1","linkman":"张三","linktel":"15210656911","PolicyId":"7","PlatCode":"017","AccountLevel":"10","BalanceMoney":"1023.0","PlatformType":"P","TravelType":"OW","InterFlights":[[{"SegmentID":"0","SegmentType":"S1","TicketingCarrier":"MU","ArrivalDate":"2016-12-03","ArrivalTime":"00:00","BoardPoint":"PEK","BoardPointAT":"","Carrier":"MU","ClassCode":"V","ClassRank":"E","DepartureDate":"2016-12-02","DepartureTime":"19:10","FlightNo":"MU9801","OffPoint":"BKK","OffPointAT":""}]],"Passengers":[{"Name":"ba/wang","PsgType":"1","CardType":"2","CardNo":"E1301230122","MobilePhone":"13264349337","Fare":"1100","ShouldPay":"1023.0","Sex":"M","BirthDay":"1980-03-07","Country":"CN","TaxAmount":"428"}]}
     */

    private String ordersn;
    private String price;
    private String status;
    private String addtime;
    private String linkman;
    private String linktel;
    private String title;
    private InfosBean infos;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getLinktel() {
        return linktel;
    }

    public void setLinktel(String linktel) {
        this.linktel = linktel;
    }

    public InfosBean getInfos() {
        return infos;
    }

    public void setInfos(InfosBean infos) {
        this.infos = infos;
    }

    public static class InfosBean {
        /**
         * uid : 1
         * linkman : 张三
         * linktel : 15210656911
         * PolicyId : 7
         * PlatCode : 017
         * AccountLevel : 10
         * BalanceMoney : 1023.0
         * PlatformType : P
         * TravelType : OW
         * InterFlights : [[{"SegmentID":"0","SegmentType":"S1","TicketingCarrier":"MU","ArrivalDate":"2016-12-03","ArrivalTime":"00:00","BoardPoint":"PEK","BoardPointAT":"","Carrier":"MU","ClassCode":"V","ClassRank":"E","DepartureDate":"2016-12-02","DepartureTime":"19:10","FlightNo":"MU9801","OffPoint":"BKK","OffPointAT":""}]]
         * Passengers : [{"Name":"ba/wang","PsgType":"1","CardType":"2","CardNo":"E1301230122","MobilePhone":"13264349337","Fare":"1100","ShouldPay":"1023.0","Sex":"M","BirthDay":"1980-03-07","Country":"CN","TaxAmount":"428"}]
         */

        private String uid;
        private String linkman;
        private String linktel;
        private String PolicyId;
        private String PlatCode;
        private String AccountLevel;
        private String BalanceMoney;
        private String PlatformType;
        private String TravelType;
        private List<List<InterFlightsBean>> InterFlights;
        private List<PlaneTicketOrderInternationalRequest.PassengersBean> Passengers;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getLinkman() {
            return linkman;
        }

        public void setLinkman(String linkman) {
            this.linkman = linkman;
        }

        public String getLinktel() {
            return linktel;
        }

        public void setLinktel(String linktel) {
            this.linktel = linktel;
        }

        public String getPolicyId() {
            return PolicyId;
        }

        public void setPolicyId(String PolicyId) {
            this.PolicyId = PolicyId;
        }

        public String getPlatCode() {
            return PlatCode;
        }

        public void setPlatCode(String PlatCode) {
            this.PlatCode = PlatCode;
        }

        public String getAccountLevel() {
            return AccountLevel;
        }

        public void setAccountLevel(String AccountLevel) {
            this.AccountLevel = AccountLevel;
        }

        public String getBalanceMoney() {
            return BalanceMoney;
        }

        public void setBalanceMoney(String BalanceMoney) {
            this.BalanceMoney = BalanceMoney;
        }

        public String getPlatformType() {
            return PlatformType;
        }

        public void setPlatformType(String PlatformType) {
            this.PlatformType = PlatformType;
        }

        public String getTravelType() {
            return TravelType;
        }

        public void setTravelType(String TravelType) {
            this.TravelType = TravelType;
        }

        public List<List<InterFlightsBean>> getInterFlights() {
            return InterFlights;
        }

        public void setInterFlights(List<List<InterFlightsBean>> InterFlights) {
            this.InterFlights = InterFlights;
        }

        public List<PlaneTicketOrderInternationalRequest.PassengersBean> getPassengers() {
            return Passengers;
        }

        public void setPassengers(List<PlaneTicketOrderInternationalRequest.PassengersBean> Passengers) {
            this.Passengers = Passengers;
        }

        @Override
        public String toString() {
            return "InfosBean{" +
                    "uid='" + uid + '\'' +
                    ", linkman='" + linkman + '\'' +
                    ", linktel='" + linktel + '\'' +
                    ", PolicyId='" + PolicyId + '\'' +
                    ", PlatCode='" + PlatCode + '\'' +
                    ", AccountLevel='" + AccountLevel + '\'' +
                    ", BalanceMoney='" + BalanceMoney + '\'' +
                    ", PlatformType='" + PlatformType + '\'' +
                    ", TravelType='" + TravelType + '\'' +
                    ", InterFlights=" + InterFlights +
                    ", Passengers=" + Passengers +
                    '}';
        }
    }
    public static class InterFlightsBean {
        /**
         * SegmentID : 0
         * SegmentType : S1
         * TicketingCarrier : MU
         * ArrivalDate : 2016-12-03
         * ArrivalTime : 00:00
         * BoardPoint : PEK
         * BoardPointAT :
         * Carrier : MU
         * ClassCode : V
         * ClassRank : E
         * DepartureDate : 2016-12-02
         * DepartureTime : 19:10
         * FlightNo : MU9801
         * OffPoint : BKK
         * OffPointAT :
         */

        private String SegmentID;
        private String SegmentType;
        private String TicketingCarrier;
        private String ArrivalDate;
        private String ArrivalTime;
        private String BoardPoint;
        private String BoardPointAT;
        private String Carrier;
        private String ClassCode;
        private String ClassRank;
        private String DepartureDate;
        private String DepartureTime;
        private String FlightNo;
        private String OffPoint;
        private String OffPointAT;

        public String getSegmentID() {
            return SegmentID;
        }

        public void setSegmentID(String SegmentID) {
            this.SegmentID = SegmentID;
        }

        public String getSegmentType() {
            return SegmentType;
        }

        public void setSegmentType(String SegmentType) {
            this.SegmentType = SegmentType;
        }

        public String getTicketingCarrier() {
            return TicketingCarrier;
        }

        public void setTicketingCarrier(String TicketingCarrier) {
            this.TicketingCarrier = TicketingCarrier;
        }

        public String getArrivalDate() {
            return ArrivalDate;
        }

        public void setArrivalDate(String ArrivalDate) {
            this.ArrivalDate = ArrivalDate;
        }

        public String getArrivalTime() {
            return ArrivalTime;
        }

        public void setArrivalTime(String ArrivalTime) {
            this.ArrivalTime = ArrivalTime;
        }

        public String getBoardPoint() {
            return BoardPoint;
        }

        public void setBoardPoint(String BoardPoint) {
            this.BoardPoint = BoardPoint;
        }

        public String getBoardPointAT() {
            return BoardPointAT;
        }

        public void setBoardPointAT(String BoardPointAT) {
            this.BoardPointAT = BoardPointAT;
        }

        public String getCarrier() {
            return Carrier;
        }

        public void setCarrier(String Carrier) {
            this.Carrier = Carrier;
        }

        public String getClassCode() {
            return ClassCode;
        }

        public void setClassCode(String ClassCode) {
            this.ClassCode = ClassCode;
        }

        public String getClassRank() {
            return ClassRank;
        }

        public void setClassRank(String ClassRank) {
            this.ClassRank = ClassRank;
        }

        public String getDepartureDate() {
            return DepartureDate;
        }

        public void setDepartureDate(String DepartureDate) {
            this.DepartureDate = DepartureDate;
        }

        public String getDepartureTime() {
            return DepartureTime;
        }

        public void setDepartureTime(String DepartureTime) {
            this.DepartureTime = DepartureTime;
        }

        public String getFlightNo() {
            return FlightNo;
        }

        public void setFlightNo(String FlightNo) {
            this.FlightNo = FlightNo;
        }

        public String getOffPoint() {
            return OffPoint;
        }

        public void setOffPoint(String OffPoint) {
            this.OffPoint = OffPoint;
        }

        public String getOffPointAT() {
            return OffPointAT;
        }

        public void setOffPointAT(String OffPointAT) {
            this.OffPointAT = OffPointAT;
        }

        @Override
        public String toString() {
            return "InterFlightsBean{" +
                    "SegmentID='" + SegmentID + '\'' +
                    ", SegmentType='" + SegmentType + '\'' +
                    ", TicketingCarrier='" + TicketingCarrier + '\'' +
                    ", ArrivalDate='" + ArrivalDate + '\'' +
                    ", ArrivalTime='" + ArrivalTime + '\'' +
                    ", BoardPoint='" + BoardPoint + '\'' +
                    ", BoardPointAT='" + BoardPointAT + '\'' +
                    ", Carrier='" + Carrier + '\'' +
                    ", ClassCode='" + ClassCode + '\'' +
                    ", ClassRank='" + ClassRank + '\'' +
                    ", DepartureDate='" + DepartureDate + '\'' +
                    ", DepartureTime='" + DepartureTime + '\'' +
                    ", FlightNo='" + FlightNo + '\'' +
                    ", OffPoint='" + OffPoint + '\'' +
                    ", OffPointAT='" + OffPointAT + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PlaneTicketDetailInternationalResponse{" +
                "ordersn='" + ordersn + '\'' +
                ", price='" + price + '\'' +
                ", status='" + status + '\'' +
                ", addtime='" + addtime + '\'' +
                ", linkman='" + linkman + '\'' +
                ", linktel='" + linktel + '\'' +
                ", title='" + title + '\'' +
                ", infos=" + infos +
                '}';
    }
}
