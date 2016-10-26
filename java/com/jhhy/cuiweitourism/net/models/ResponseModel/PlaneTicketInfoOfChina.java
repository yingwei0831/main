package com.jhhy.cuiweitourism.net.models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by zhangguang on 16/10/25.
 */
public class PlaneTicketInfoOfChina {


    public static class PolicyDataInfo{
        //"comment": "B2B电子客票请不要做RR，否则可能无法出票。  ",
        //"commisionMoney": 10,
        //"commisionPoint": "0.0",
        //"commisionType": 0,
        //"policyBelongTo": 2,
        //"policyId": 178285482,
        //"policyType": "B2B",
        //"seatType": 1,
        //"vtWorkTime": "08:00-23:00",
        //"workTime": "00:00-23:59"

        public String comment;
        public String commisionMoney;
        public String commisionPoint;
        public String commisionType;
        public String policyBelongTo;
        public String policyId;
        public String policyType;
        public String seatType;
        public String vtWorkTime;
        public String workTime;

        public PolicyDataInfo(String comment, String commisionMoney, String commisionPoint, String commisionType, String policyBelongTo, String policyId, String policyType, String seatType, String vtWorkTime, String workTime) {
            this.comment = comment;
            this.commisionMoney = commisionMoney;
            this.commisionPoint = commisionPoint;
            this.commisionType = commisionType;
            this.policyBelongTo = policyBelongTo;
            this.policyId = policyId;
            this.policyType = policyType;
            this.seatType = seatType;
            this.vtWorkTime = vtWorkTime;
            this.workTime = workTime;
        }

        public PolicyDataInfo() {
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommisionMoney() {
            return commisionMoney;
        }

        public void setCommisionMoney(String commisionMoney) {
            this.commisionMoney = commisionMoney;
        }

        public String getCommisionPoint() {
            return commisionPoint;
        }

        public void setCommisionPoint(String commisionPoint) {
            this.commisionPoint = commisionPoint;
        }

        public String getCommisionType() {
            return commisionType;
        }

        public void setCommisionType(String commisionType) {
            this.commisionType = commisionType;
        }

        public String getPolicyBelongTo() {
            return policyBelongTo;
        }

        public void setPolicyBelongTo(String policyBelongTo) {
            this.policyBelongTo = policyBelongTo;
        }

        public String getPolicyId() {
            return policyId;
        }

        public void setPolicyId(String policyId) {
            this.policyId = policyId;
        }

        public String getPolicyType() {
            return policyType;
        }

        public void setPolicyType(String policyType) {
            this.policyType = policyType;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public String getVtWorkTime() {
            return vtWorkTime;
        }

        public void setVtWorkTime(String vtWorkTime) {
            this.vtWorkTime = vtWorkTime;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        @Override
        public String toString() {
            return "PolicyDataInfo{" +
                    "comment='" + comment + '\'' +
                    ", commisionMoney='" + commisionMoney + '\'' +
                    ", commisionPoint='" + commisionPoint + '\'' +
                    ", commisionType='" + commisionType + '\'' +
                    ", policyBelongTo='" + policyBelongTo + '\'' +
                    ", policyId='" + policyId + '\'' +
                    ", policyType='" + policyType + '\'' +
                    ", seatType='" + seatType + '\'' +
                    ", vtWorkTime='" + vtWorkTime + '\'' +
                    ", workTime='" + workTime + '\'' +
                    '}';
        }
    }

    public static class SeatItemInfo{
        //"discount": 0.46,
        //"flightNo": "CZ6132",
        //"parPrice": 330,
        //"param2": "3",
        //"policyData": {},
        //"seatCode": "V",
        //"seatMsg": "经济舱",
        //"seatStatus": "A",
        //"seatType": 1,
        //"settlePrice": 320

        public String discount;
        public String flightNo;
        public String parPrice;
        public String param2;
        public PolicyDataInfo policyData;
        public String seatCode;
        public String seatMsg;
        public String seatStatus;
        public String seatType;
        public String settlePrice;

        public SeatItemInfo(String discount, String flightNo, String parPrice, String param2, PolicyDataInfo policyData, String seatCode, String seatMsg, String seatStatus, String seatType, String settlePrice) {
            this.discount = discount;
            this.flightNo = flightNo;
            this.parPrice = parPrice;
            this.param2 = param2;
            this.policyData = policyData;
            this.seatCode = seatCode;
            this.seatMsg = seatMsg;
            this.seatStatus = seatStatus;
            this.seatType = seatType;
            this.settlePrice = settlePrice;
        }

        public SeatItemInfo() {
        }

        public String getDiscount() {
            return discount;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getParPrice() {
            return parPrice;
        }

        public void setParPrice(String parPrice) {
            this.parPrice = parPrice;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }

        public PolicyDataInfo getPolicyData() {
            return policyData;
        }

        public void setPolicyData(PolicyDataInfo policyData) {
            this.policyData = policyData;
        }

        public String getSeatCode() {
            return seatCode;
        }

        public void setSeatCode(String seatCode) {
            this.seatCode = seatCode;
        }

        public String getSeatMsg() {
            return seatMsg;
        }

        public void setSeatMsg(String seatMsg) {
            this.seatMsg = seatMsg;
        }

        public String getSeatStatus() {
            return seatStatus;
        }

        public void setSeatStatus(String seatStatus) {
            this.seatStatus = seatStatus;
        }

        public String getSeatType() {
            return seatType;
        }

        public void setSeatType(String seatType) {
            this.seatType = seatType;
        }

        public String getSettlePrice() {
            return settlePrice;
        }

        public void setSettlePrice(String settlePrice) {
            this.settlePrice = settlePrice;
        }

        @Override
        public String toString() {
            return "SeatItemInfo{" +
                    "discount='" + discount + '\'' +
                    ", flightNo='" + flightNo + '\'' +
                    ", parPrice='" + parPrice + '\'' +
                    ", param2='" + param2 + '\'' +
                    ", policyData=" + policyData +
                    ", seatCode='" + seatCode + '\'' +
                    ", seatMsg='" + seatMsg + '\'' +
                    ", seatStatus='" + seatStatus + '\'' +
                    ", seatType='" + seatType + '\'' +
                    ", settlePrice='" + settlePrice + '\'' +
                    '}';
        }
    }



    public static class FlightInfo{
        //"airportTax": 50,
        //"arriModifyTime": "",
        //"arriTime": "0930",
        //"codeShare": false,
        //"depTime": "0805",
        //"dstCity": "DLC",
        //"dstJetquay": "--",
        //"flightNo": "CZ6132",
        //"fuelTax": 0,
        //"isElectronicTicket": true,
        //"link": "DS#",
        //"meal": "true",
        //"orgCity": "PEK",
        //"orgJetquay": "T2",
        //"param1": "2016-11-17",
        //"param2": "0.0",
        //"param3": "0.0",
        //"planeType": "321",
        //"seatItems": [],
        //"shareNum": "",
        //"stopnum": 0

        public String airportTax;
        public String arriModifyTime;
        public String arriTime;
        public String codeShare;
        public String depTime;
        public String dstCity;
        public String dstJetquay;
        public String flightNo;
        public String fuelTax;
        public String isElectronicTicket;
        public String link;
        public String meal;
        public String orgCity;
        public String orgJetquay;
        public String param1;
        public String param2;
        public String param3;
        public String planeType;
        public ArrayList<SeatItemInfo> seatItems;
        public String shareNum;
        public String stopnum;


        public FlightInfo(String airportTax, String arriModifyTime, String arriTime, String codeShare, String depTime, String dstCity, String dstJetquay, String flightNo, String fuelTax, String isElectronicTicket, String link, String meal, String orgCity, String orgJetquay, String param1, String param2, String param3, String planeType, ArrayList<SeatItemInfo> seatItems, String shareNum, String stopnum) {
            this.airportTax = airportTax;
            this.arriModifyTime = arriModifyTime;
            this.arriTime = arriTime;
            this.codeShare = codeShare;
            this.depTime = depTime;
            this.dstCity = dstCity;
            this.dstJetquay = dstJetquay;
            this.flightNo = flightNo;
            this.fuelTax = fuelTax;
            this.isElectronicTicket = isElectronicTicket;
            this.link = link;
            this.meal = meal;
            this.orgCity = orgCity;
            this.orgJetquay = orgJetquay;
            this.param1 = param1;
            this.param2 = param2;
            this.param3 = param3;
            this.planeType = planeType;
            this.seatItems = seatItems;
            this.shareNum = shareNum;
            this.stopnum = stopnum;
        }

        public FlightInfo() {
        }

        public String getAirportTax() {
            return airportTax;
        }

        public void setAirportTax(String airportTax) {
            this.airportTax = airportTax;
        }

        public String getArriModifyTime() {
            return arriModifyTime;
        }

        public void setArriModifyTime(String arriModifyTime) {
            this.arriModifyTime = arriModifyTime;
        }

        public String getArriTime() {
            return arriTime;
        }

        public void setArriTime(String arriTime) {
            this.arriTime = arriTime;
        }

        public String getCodeShare() {
            return codeShare;
        }

        public void setCodeShare(String codeShare) {
            this.codeShare = codeShare;
        }

        public String getDepTime() {
            return depTime;
        }

        public void setDepTime(String depTime) {
            this.depTime = depTime;
        }

        public String getDstCity() {
            return dstCity;
        }

        public void setDstCity(String dstCity) {
            this.dstCity = dstCity;
        }

        public String getDstJetquay() {
            return dstJetquay;
        }

        public void setDstJetquay(String dstJetquay) {
            this.dstJetquay = dstJetquay;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getFuelTax() {
            return fuelTax;
        }

        public void setFuelTax(String fuelTax) {
            this.fuelTax = fuelTax;
        }

        public String getIsElectronicTicket() {
            return isElectronicTicket;
        }

        public void setIsElectronicTicket(String isElectronicTicket) {
            this.isElectronicTicket = isElectronicTicket;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getMeal() {
            return meal;
        }

        public void setMeal(String meal) {
            this.meal = meal;
        }

        public String getOrgCity() {
            return orgCity;
        }

        public void setOrgCity(String orgCity) {
            this.orgCity = orgCity;
        }

        public String getOrgJetquay() {
            return orgJetquay;
        }

        public void setOrgJetquay(String orgJetquay) {
            this.orgJetquay = orgJetquay;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

        public String getParam2() {
            return param2;
        }

        public void setParam2(String param2) {
            this.param2 = param2;
        }

        public String getParam3() {
            return param3;
        }

        public void setParam3(String param3) {
            this.param3 = param3;
        }

        public String getPlaneType() {
            return planeType;
        }

        public void setPlaneType(String planeType) {
            this.planeType = planeType;
        }

        public ArrayList<SeatItemInfo> getSeatItems() {
            return seatItems;
        }

        public void setSeatItems(ArrayList<SeatItemInfo> seatItems) {
            this.seatItems = seatItems;
        }

        public String getShareNum() {
            return shareNum;
        }

        public void setShareNum(String shareNum) {
            this.shareNum = shareNum;
        }

        public String getStopnum() {
            return stopnum;
        }

        public void setStopnum(String stopnum) {
            this.stopnum = stopnum;
        }

        @Override
        public String toString() {
            return "FlightInfo{" +
                    "airportTax='" + airportTax + '\'' +
                    ", arriModifyTime='" + arriModifyTime + '\'' +
                    ", arriTime='" + arriTime + '\'' +
                    ", codeShare='" + codeShare + '\'' +
                    ", depTime='" + depTime + '\'' +
                    ", dstCity='" + dstCity + '\'' +
                    ", dstJetquay='" + dstJetquay + '\'' +
                    ", flightNo='" + flightNo + '\'' +
                    ", fuelTax='" + fuelTax + '\'' +
                    ", isElectronicTicket='" + isElectronicTicket + '\'' +
                    ", link='" + link + '\'' +
                    ", meal='" + meal + '\'' +
                    ", orgCity='" + orgCity + '\'' +
                    ", orgJetquay='" + orgJetquay + '\'' +
                    ", param1='" + param1 + '\'' +
                    ", param2='" + param2 + '\'' +
                    ", param3='" + param3 + '\'' +
                    ", planeType='" + planeType + '\'' +
                    ", seatItems=" + seatItems +
                    ", shareNum='" + shareNum + '\'' +
                    ", stopnum='" + stopnum + '\'' +
                    '}';
        }
    }




    public  static  class  FlightItemsInfo{
//        "audletAirportTax": 50,
//                "audletFuelTax": 0,
//                "basePrice": 710,
//                "date": "2016-11-17",
//                "distance": 579,
//                "dstCity": "DLC",
//                "flights": [],
//                "orgCity": "PEK"

        public  String audletAirportTax;
        public  String audletFuelTax;
        public  String basePrice;
        public  String date;
        public  String distance;
        public  String dstCity;
        public ArrayList<FlightInfo> flights;
        public  String orgCity;

        public FlightItemsInfo(String audletAirportTax, String audletFuelTax, String basePrice, String date, String distance, String dstCity, ArrayList<FlightInfo> flights, String orgCity) {
            this.audletAirportTax = audletAirportTax;
            this.audletFuelTax = audletFuelTax;
            this.basePrice = basePrice;
            this.date = date;
            this.distance = distance;
            this.dstCity = dstCity;
            this.flights = flights;
            this.orgCity = orgCity;
        }

        public FlightItemsInfo() {
        }

        public String getAudletAirportTax() {
            return audletAirportTax;
        }

        public void setAudletAirportTax(String audletAirportTax) {
            this.audletAirportTax = audletAirportTax;
        }

        public String getAudletFuelTax() {
            return audletFuelTax;
        }

        public void setAudletFuelTax(String audletFuelTax) {
            this.audletFuelTax = audletFuelTax;
        }

        public String getBasePrice() {
            return basePrice;
        }

        public void setBasePrice(String basePrice) {
            this.basePrice = basePrice;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getDstCity() {
            return dstCity;
        }

        public void setDstCity(String dstCity) {
            this.dstCity = dstCity;
        }

        public ArrayList<FlightInfo> getFlights() {
            return flights;
        }

        public void setFlights(ArrayList<FlightInfo> flights) {
            this.flights = flights;
        }

        public String getOrgCity() {
            return orgCity;
        }

        public void setOrgCity(String orgCity) {
            this.orgCity = orgCity;
        }

        @Override
        public String toString() {
            return "FlightItemsInfo{" +
                    "audletAirportTax='" + audletAirportTax + '\'' +
                    ", audletFuelTax='" + audletFuelTax + '\'' +
                    ", basePrice='" + basePrice + '\'' +
                    ", date='" + date + '\'' +
                    ", distance='" + distance + '\'' +
                    ", dstCity='" + dstCity + '\'' +
                    ", flights=" + flights +
                    ", orgCity='" + orgCity + '\'' +
                    '}';
        }
    }


    public static class ReturnInfo{
//        "returnCode": "S",
//        "flightItems": {}

        public String returnCode;
        public FlightItemsInfo flightItems;

        public ReturnInfo(String returnCode, FlightItemsInfo flightItems) {
            this.returnCode = returnCode;
            this.flightItems = flightItems;
        }

        public ReturnInfo() {
        }

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public FlightItemsInfo getFlightItems() {
            return flightItems;
        }

        public void setFlightItems(FlightItemsInfo flightItems) {
            this.flightItems = flightItems;
        }

        @Override
        public String toString() {
            return "ReturnInfo{" +
                    "returnCode='" + returnCode + '\'' +
                    ", flightItems=" + flightItems +
                    '}';
        }
    }

    @SerializedName("return")
    public ReturnInfo returnInfo;


    public PlaneTicketInfoOfChina(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }

    public PlaneTicketInfoOfChina() {
    }

    public ReturnInfo getReturnInfo() {
        return returnInfo;
    }

    public void setReturnInfo(ReturnInfo returnInfo) {
        this.returnInfo = returnInfo;
    }

    @Override
    public String toString() {
        return "PlanTicketInfoOfChina{" +
                "returnInfo=" + returnInfo +
                '}';
    }
}
