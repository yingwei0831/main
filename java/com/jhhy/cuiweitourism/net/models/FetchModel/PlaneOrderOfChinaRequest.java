package com.jhhy.cuiweitourism.net.models.FetchModel;

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
     */

    public String policyId;
    public String linkMan;
    public String linkPhone;

    public PnrInfoBean pnrInfo;

    public String memberid;
    public String commisionPoint;
    public String commisionMoney;

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

    public static class PnrInfoBean {
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

        @Override
        public String toString() {
            return "PnrInfoBean{" +
                    "airportTax='" + airportTax + '\'' +
                    ", fuelTax='" + fuelTax + '\'' +
                    ", parPrice='" + parPrice + '\'' +
                    ", passengers=" + passengers +
                    ", segments=" + segments +
                    '}';
        }
    }
    public static class PassengersBean {
//         "passengers":[
//              {"birthday":"","identityNo":"211389598745869587","identityType":"1","name":"秦绍名","type":"0","param1":"15210656911"}
//         ],
        /**
         * birthday :
         * identityNo : 211389598745869587
         * identityType : 1
         * name : 秦绍名
         * type : 0
         * param1 : 15210656911
         */

        public String birthday;
        public String identityNo;
        public String identityType;
        public String name;
        public String type;
        public String param1;

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

        @Override
        public String toString() {
            return "PassengersBean{" +
                    "birthday='" + birthday + '\'' +
                    ", identityNo='" + identityNo + '\'' +
                    ", identityType='" + identityType + '\'' +
                    ", name='" + name + '\'' +
                    ", type='" + type + '\'' +
                    ", param1='" + param1 + '\'' +
                    '}';
        }
    }

    public static class SegmentsBean {
//          "segments":[
//              {"arrCode":"DLC","arrTime":"0930","depCode":"PEK","depDate":"2016-12-17","depTime":"0805","flightNo":"CZ6132","planeModel":"321","seatClass":"V"}
//          ]
        /**
         * arrCode : DLC
         * arrTime : 0930
         * depCode : PEK
         * depDate : 2016-12-17
         * depTime : 0805
         * flightNo : CZ6132
         * planeModel : 321
         * seatClass : V
         */

        public String arrCode;
        public String arrTime;
        public String depCode;
        public String depDate;
        public String depTime;
        public String flightNo;
        public String planeModel;
        public String seatClass;

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

        @Override
        public String toString() {
            return "SegmentsBean{" +
                    "arrCode='" + arrCode + '\'' +
                    ", arrTime='" + arrTime + '\'' +
                    ", depCode='" + depCode + '\'' +
                    ", depDate='" + depDate + '\'' +
                    ", depTime='" + depTime + '\'' +
                    ", flightNo='" + flightNo + '\'' +
                    ", planeModel='" + planeModel + '\'' +
                    ", seatClass='" + seatClass + '\'' +
                    '}';
        }
    }
    @Override
    public String toString() {
        return "PlaneOrderOfChinaRequest{" +
                "policyId='" + policyId + '\'' +
                ", linkMan='" + linkMan + '\'' +
                ", linkPhone='" + linkPhone + '\'' +
                ", pnrInfo=" + pnrInfo +
                ", memberid='" + memberid + '\'' +
                ", commisionPoint='" + commisionPoint + '\'' +
                ", commisionMoney='" + commisionMoney + '\'' +
                '}';
    }
}
