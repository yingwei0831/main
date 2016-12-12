package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/9.
 * 国际机票退改签政策请求
 */
public class PlaneTicketInternationalChangeBack extends BasicFetchModel {


    private List<AirRulesRQBean> AirRulesRQ;

    public PlaneTicketInternationalChangeBack(List<AirRulesRQBean> airRulesRQ) {
        AirRulesRQ = airRulesRQ;
    }

    public List<AirRulesRQBean> getAirRulesRQ() {
        return AirRulesRQ;
    }

    public void setAirRulesRQ(List<AirRulesRQBean> AirRulesRQ) {
        this.AirRulesRQ = AirRulesRQ;
    }

    public static class AirRulesRQBean extends BasicFetchModel{

        private String DepartureDate;
        private String DepartureTime;
        private String FareReference;
        private String FilingAirline;
        private String DepartureAirport;
        private String ArrivalAirport;
        private String References;

        /**
         * DepartureDate : 2016-12-17       出发日期,从航班查询S节点获得
         * DepartureTime : 19:10            出发时间,从航班查询S节点获得
         * FareReference : VPRWCS           运价基础,从H节点获得
         * FilingAirline : MU               发布运价的航司, 从H节点获得
         * DepartureAirport : BJS           出发机场,从H节点获得
         * ArrivalAirport : BKK             到达机场,从H节点获得
         * References : QlpoOTFBWSZTWdrDgV0AAKLfgEASQeT%2B7T/zXiorh1wAMACywinqAAAAAADTQACVNNU9TI9QGj1A9Q0B6jQAaBKJQMmgNGgGIyaDR6mjR%2BqNCAKVuZAB4W0ZRFVluqlVDI8TE0kIQRF5zKdEcqspZGNpMumZRz2s8DbvGmdxZ2bW56HzWGT/gCsAH8K6gEY3KxNhqpIyU8ELMk4hIQYlEaZFHg2NBsqCRx5xmtCqK8gigyLnjfE6%2B4Oyb3gH4rEY6HwtEeAWgDhgBQYBgGEREQP4u5IpwoSG1hwK6A==
         */                                 //退改签Sign,从H节点获得
        public AirRulesRQBean(String departureDate, String departureTime, String fareReference, String filingAirline, String departureAirport, String arrivalAirport, String references) {
            DepartureDate = departureDate;
            DepartureTime = departureTime;
            FareReference = fareReference;
            FilingAirline = filingAirline;
            DepartureAirport = departureAirport;
            ArrivalAirport = arrivalAirport;
            References = references;
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

        public String getFareReference() {
            return FareReference;
        }

        public void setFareReference(String FareReference) {
            this.FareReference = FareReference;
        }

        public String getFilingAirline() {
            return FilingAirline;
        }

        public void setFilingAirline(String FilingAirline) {
            this.FilingAirline = FilingAirline;
        }

        public String getDepartureAirport() {
            return DepartureAirport;
        }

        public void setDepartureAirport(String DepartureAirport) {
            this.DepartureAirport = DepartureAirport;
        }

        public String getArrivalAirport() {
            return ArrivalAirport;
        }

        public void setArrivalAirport(String ArrivalAirport) {
            this.ArrivalAirport = ArrivalAirport;
        }

        public String getReferences() {
            return References;
        }

        public void setReferences(String References) {
            this.References = References;
        }
    }
}
