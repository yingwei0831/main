package com.jhhy.cuiweitourism.net.models.FetchModel;

import com.jhhy.cuiweitourism.net.models.FetchModel.BasicFetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/13.
 * 国际机票政策匹配（验价）
 */
public class PlaneTicketInternationalPolicyCheckRequest extends BasicFetchModel {

    /**
     * TravelType : OW
     * A : [
     *          [
     *              {"SegmentID":"0","SegmentType":"S1","TicketingCarrier":"MU","ArrivalDate":"2016-12-03","ArrivalTime":"00:00","BoardPoint":"PEK","BoardPointAT":"","Carrier":"MU","ClassCode":"V","ClassRank":"E","DepartureDate":"2016-12-02","DepartureTime":"19:10","FlightNo":"MU9801","OffPoint":"BKK","OffPointAT":""}
     *          ]
     *     ]
     * Gds : 1E
     * Plats : ALL
     */

    private String TravelType;
    private String Gds;
    private String Plats;
    private List<List<IFlight>> A; //[S1,S2]

    public PlaneTicketInternationalPolicyCheckRequest(String travelType, String gds, String plats, List<List<IFlight>> a) {
        TravelType = travelType;
        Gds = gds;
        Plats = plats;
        A = a;
    }

    public String getTravelType() {
        return TravelType;
    }

    public void setTravelType(String TravelType) {
        this.TravelType = TravelType;
    }

    public String getGds() {
        return Gds;
    }

    public void setGds(String Gds) {
        this.Gds = Gds;
    }

    public String getPlats() {
        return Plats;
    }

    public void setPlats(String Plats) {
        this.Plats = Plats;
    }

    public List<List<IFlight>> getA() {
        return A;
    }

    public void setA(List<List<IFlight>> A) {
        this.A = A;
    }

    public static class IFlight extends BasicFetchModel{
        /**
         * SegmentID : 0            航段
         * SegmentType : S1         去程/返程
         * TicketingCarrier : MU    主承运人,整个行程的主承运人,从H节点获得
         * ArrivalDate : 2016-12-03 到达日期
         * ArrivalTime : 00:00      到达时间
         * BoardPoint : PEK         出发城市
         * BoardPointAT :           出发航站楼
         * Carrier : MU             航司编码
         * ClassCode : V            舱位
         * ClassRank : E            舱位类型    B:公务舱   E:经济舱   ES:高端经济舱    F:头等舱
         * DepartureDate : 2016-12-02   出发日期
         * DepartureTime : 19:10        出发时间
         * FlightNo : MU9801            航班号
         * OffPoint : BKK               到达城市
         * OffPointAT :                 到达航站楼
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

        public IFlight(String segmentID, String segmentType, String ticketingCarrier, String arrivalDate, String arrivalTime, String boardPoint, String boardPointAT,
                       String carrier, String classCode,
                       String departureDate, String departureTime, String flightNo, String offPoint, String offPointAT) {
            SegmentID = segmentID;
            SegmentType = segmentType;
            TicketingCarrier = ticketingCarrier;
            ArrivalDate = arrivalDate;
            ArrivalTime = arrivalTime;
            BoardPoint = boardPoint;
            BoardPointAT = boardPointAT;
            Carrier = carrier;
            ClassCode = classCode;

            DepartureDate = departureDate;
            DepartureTime = departureTime;
            FlightNo = flightNo;
            OffPoint = offPoint;
            OffPointAT = offPointAT;
        }

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
    }
}
