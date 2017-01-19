package com.jhhy.cuiweitourism.net.models.FetchModel;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/8.
 */
public class PlaneOrderOfChinaRequest extends BasicFetchModel {

//   {"head":{"code":"Fly_order"},"field":
//{"policyId":"185554103","linkMan":"秦绍名","linkPhone":"15210656332",
//"pnrInfo":{"airportTax":"50","fuelTax":"0","parPrice":"360",
//         "passengers":[
//              {"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}
//         ],
//          "segments":[
//              {"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}
//          ]
// },
//"memberid":"52","commisionPoint":"0.0","commisionMoney":"14"}
// }
    /**
     * policyId : 185554103
     * linkMan : 秦绍名
     * linkPhone : 15210656332
     * pnrInfo : {"airportTax":"50","fuelTax":"0","parPrice":"360","passengers":[{"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}],"segments":[{"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}]}
     * memberid : 52
     * commisionPoint : 0.0
     * commisionMoney : 14
     * settlePrice : 386
     */

    public String policyId;
    public String linkMan;
    public String linkPhone;

    public PnrInfoBean pnrInfo;

    public String memberid;
    public String commisionPoint;
    public String commisionMoney;
    public String settlePrice;
    public String needjifen;

    public PlaneOrderOfChinaRequest(String policyId, String linkMan, String linkPhone, PnrInfoBean pnrInfo, String memberid,
                                    String commisionPoint, String commisionMoney, String settlePrice, String needJiFen) {
        this.policyId = policyId;
        this.linkMan = linkMan;
        this.linkPhone = linkPhone;
        this.pnrInfo = pnrInfo;
        this.memberid = memberid;
        this.commisionPoint = commisionPoint;
        this.commisionMoney = commisionMoney;
        this.settlePrice = settlePrice;
        needjifen = needJiFen;
    }

    public String getSettlePrice() {
        return settlePrice;
    }

    public void setSettlePrice(String settlePrice) {
        this.settlePrice = settlePrice;
    }

    public String getPolicyId() {
        return policyId;
    }

    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }

    public String getLinkMan() {
        return linkMan;
    }

    public void setLinkMan(String linkMan) {
        this.linkMan = linkMan;
    }

    public String getLinkPhone() {
        return linkPhone;
    }

    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    public PnrInfoBean getPnrInfo() {
        return pnrInfo;
    }

    public void setPnrInfo(PnrInfoBean pnrInfo) {
        this.pnrInfo = pnrInfo;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getCommisionPoint() {
        return commisionPoint;
    }

    public void setCommisionPoint(String commisionPoint) {
        this.commisionPoint = commisionPoint;
    }

    public String getCommisionMoney() {
        return commisionMoney;
    }

    public void setCommisionMoney(String commisionMoney) {
        this.commisionMoney = commisionMoney;
    }

    public static class PnrInfoBean extends BasicFetchModel{
//"pnrInfo":{"airportTax":"50","fuelTax":"0","parPrice":"360",
//         "passengers":[
//              {"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}
//         ],
//          "segments":[
//              {"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}
//          ]
// },
        /**
         * airportTax : 50
         * fuelTax : 0
         * parPrice : 360
         * passengers : [{"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}]
         * segments : [{"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}]
         */

        public String airportTax;
        public String fuelTax;
        public String parPrice;

        public List<PassengersBean> passengers;
        public List<SegmentsBean> segments;

        public PnrInfoBean(String airportTax, String fuelTax, String parPrice, List<PassengersBean> passengers, List<SegmentsBean> segments) {
            this.airportTax = airportTax;
            this.fuelTax = fuelTax;
            this.parPrice = parPrice;
            this.passengers = passengers;
            this.segments = segments;
        }

        public PnrInfoBean() {
        }

        public String getAirportTax() {
            return airportTax;
        }

        public void setAirportTax(String airportTax) {
            this.airportTax = airportTax;
        }

        public String getFuelTax() {
            return fuelTax;
        }

        public void setFuelTax(String fuelTax) {
            this.fuelTax = fuelTax;
        }

        public String getParPrice() {
            return parPrice;
        }

        public void setParPrice(String parPrice) {
            this.parPrice = parPrice;
        }

        public List<PassengersBean> getPassengers() {
            return passengers;
        }

        public void setPassengers(List<PassengersBean> passengers) {
            this.passengers = passengers;
        }

        public List<SegmentsBean> getSegments() {
            return segments;
        }

        public void setSegments(List<SegmentsBean> segments) {
            this.segments = segments;
        }

    }
    public static class PassengersBean extends BasicFetchModel{
//         "passengers":[
//              {"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}
//         ],
        /**
         * birthday :
         * identityNo : 211389598745869587
         * identityType : 1     //=1 身份证； =2 护照； =3 军官证；  =4 士兵证； =5 台胞证； =6 其他. （支持港澳通行证，需在登机时出示可证明其身份的证明即可）
         * name : 秦绍名
         * type : 0             //=0 成人; =1儿童；
         * param1 : 15210656911
         */
//  XX  1:1代身份证 2:2代身份证 3：港澳通信证 4：台湾通行证 5：护照
        public String birthday;
        public String identityNo;
        public String identityType;
        public String name;
        public String type;
        public String param1;

        public PassengersBean() {
        }

        public PassengersBean(String birthday, String identityNo, String identityType, String name, String type, String param1) {
            this.birthday = birthday;
            this.identityNo = identityNo;
            this.identityType = identityType;
            this.name = name;
            this.type = type;
            this.param1 = param1;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getIdentityNo() {
            return identityNo;
        }

        public void setIdentityNo(String identityNo) {
            this.identityNo = identityNo;
        }

        public String getIdentityType() {
            return identityType;
        }

        public void setIdentityType(String identityType) {
            this.identityType = identityType;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getParam1() {
            return param1;
        }

        public void setParam1(String param1) {
            this.param1 = param1;
        }

    }

    public static class SegmentsBean extends BasicFetchModel{
//          "segments":[
//              {"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}
//          ]
        /**
         * arrCode : DLC        抵达地
         * arrTime : 0930       抵达时间
         * depCode : PEK        出发地
         * depDate : 2016-12-17 出发日期
         * depTime : 0805       出发时间
         * flightNo : CZ6132    航班号
         * planeModel : 321     飞机型号
         * seatClass : V        舱位
         */

        public String arrCode;
        public String arrTime;
        public String depCode;
        public String depDate;
        public String depTime;
        public String flightNo;
        public String planeModel;
        public String seatClass;

        public SegmentsBean() {
        }

        public SegmentsBean(String arrCode, String arrTime, String depCode, String depDate, String depTime, String flightNo, String planeModel, String seatClass) {
            this.arrCode = arrCode;
            this.arrTime = arrTime;
            this.depCode = depCode;
            this.depDate = depDate;
            this.depTime = depTime;
            this.flightNo = flightNo;
            this.planeModel = planeModel;
            this.seatClass = seatClass;
        }

        public String getArrCode() {
            return arrCode;
        }

        public void setArrCode(String arrCode) {
            this.arrCode = arrCode;
        }

        public String getArrTime() {
            return arrTime;
        }

        public void setArrTime(String arrTime) {
            this.arrTime = arrTime;
        }

        public String getDepCode() {
            return depCode;
        }

        public void setDepCode(String depCode) {
            this.depCode = depCode;
        }

        public String getDepDate() {
            return depDate;
        }

        public void setDepDate(String depDate) {
            this.depDate = depDate;
        }

        public String getDepTime() {
            return depTime;
        }

        public void setDepTime(String depTime) {
            this.depTime = depTime;
        }

        public String getFlightNo() {
            return flightNo;
        }

        public void setFlightNo(String flightNo) {
            this.flightNo = flightNo;
        }

        public String getPlaneModel() {
            return planeModel;
        }

        public void setPlaneModel(String planeModel) {
            this.planeModel = planeModel;
        }

        public String getSeatClass() {
            return seatClass;
        }

        public void setSeatClass(String seatClass) {
            this.seatClass = seatClass;
        }


    }

}
