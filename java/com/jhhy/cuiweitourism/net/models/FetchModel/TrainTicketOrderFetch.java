package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.ArrayList;

/**
 * Created by birneysky on 16/10/11.
 */
public class TrainTicketOrderFetch extends BasicFetchModel {
    //        "PsgName": "人1",
    //        "CardType": "2", //1:1代身份证 2:2代身份证 3：港澳通信证 4：台湾通行证 5：护照
    //        "CardNo": "211382198608262687",
    //        "TicketType": "0", //0：成人票 1：儿童票
    //        "SeatType": "6", //0：商务座 1：特等座 2：一等座 3：二等座 4：高级软卧 5：软卧 6：硬卧 7：软座 8：硬座 9：无座
    //        "TicketPrice": "121.50" //车票单价
    static public class TicketInfo extends BasicFetchModel{
        public String PsgName;
        public String CardType;
        public String CardNo;
        public String TicketType;
        public String SeatType;
        public String TicketPrice;

        public TicketInfo(String psgName, String cardType, String cardNo, String ticketType, String seatType, String ticketPrice) {
            PsgName = psgName;
            CardType = cardType;
            CardNo = cardNo;
            TicketType = ticketType;
            SeatType = seatType;
            TicketPrice = ticketPrice;
        }

        public TicketInfo() {
        }

        public String getPsgName() {
            return PsgName;
        }

        public void setPsgName(String psgName) {
            PsgName = psgName;
        }

        public String getCardType() {
            return CardType;
        }

        public void setCardType(String cardType) {
            CardType = cardType;
        }

        public String getCardNo() {
            return CardNo;
        }

        public void setCardNo(String cardNo) {
            CardNo = cardNo;
        }

        public String getTicketType() {
            return TicketType;
        }

        public void setTicketType(String ticketType) {
            TicketType = ticketType;
        }

        public String getSeatType() {
            return SeatType;
        }

        public void setSeatType(String seatType) {
            SeatType = seatType;
        }

        public String getTicketPrice() {
            return TicketPrice;
        }

        public void setTicketPrice(String ticketPrice) {
            TicketPrice = ticketPrice;
        }
    }


//    "memberid": "",
//            "linkman": "",
//            "linktel": "",
//            "FromStation": "北京",
//            "ToStation": "凌源",
//            "TrainCode": "2257",
//            "TrainDate": "2016-09-30",
//            "FromTime": "12:20",
//            "ToDate": "2016-09-30",
//            "ToTime": "21:11",
//            "goupiaoren": [
//    {
//        "PsgName": "人1",
//            "CardType": "2",
//            "CardNo": "211382198608262687",
//            "TicketType": "0",
//            "SeatType": "6",
//            "TicketPrice": "121.50"
//    },
//    {
//        "PsgName": "人2",
//            "CardType": "2",
//            "CardNo": "211382198608262698",
//            "TicketType": "0",
//            "SeatType": "6",
//            "TicketPrice": "121.50"
//    }
//    ],
//            "SeatType": "6",
//            "SumPrice": "243"


    public String memberid;
    public String linkman;
    public String linktel;
    public String FromStation;
    public String ToStation;
    public String TrainCode;
    public String TrainDate;
    public String FromTime;
    public String ToDate;
    public String ToTime;
    public ArrayList<TicketInfo> goupiaoren;
    public String SeatType;
    public String SumPrice;
    public String needjifen;

    public TrainTicketOrderFetch(String memberid, String linkman, String linktel, String fromStation, String toStation, String trainCode, String trainDate,
                                 String fromTime, String toDate, String toTime, ArrayList<TicketInfo> goupiaoren, String seatType, String sumPrice, String needjifen) {
        this.memberid = memberid;
        this.linkman = linkman;
        this.linktel = linktel;
        FromStation = fromStation;
        ToStation = toStation;
        TrainCode = trainCode;
        TrainDate = trainDate;
        FromTime = fromTime;
        ToDate = toDate;
        ToTime = toTime;
        this.goupiaoren = goupiaoren;
        SeatType = seatType;
        SumPrice = sumPrice;
        this.needjifen = needjifen;
    }

    public TrainTicketOrderFetch() {
    }

    public String getNeedjifen() {
        return needjifen;
    }

    public void setNeedjifen(String needjifen) {
        this.needjifen = needjifen;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
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

    public String getFromStation() {
        return FromStation;
    }

    public void setFromStation(String fromStation) {
        FromStation = fromStation;
    }

    public String getToStation() {
        return ToStation;
    }

    public void setToStation(String toStation) {
        ToStation = toStation;
    }

    public String getTrainCode() {
        return TrainCode;
    }

    public void setTrainCode(String trainCode) {
        TrainCode = trainCode;
    }

    public String getTrainDate() {
        return TrainDate;
    }

    public void setTrainDate(String trainDate) {
        TrainDate = trainDate;
    }

    public String getFromTime() {
        return FromTime;
    }

    public void setFromTime(String fromTime) {
        FromTime = fromTime;
    }

    public String getToDate() {
        return ToDate;
    }

    public void setToDate(String toDate) {
        ToDate = toDate;
    }

    public String getToTime() {
        return ToTime;
    }

    public void setToTime(String toTime) {
        ToTime = toTime;
    }

    public ArrayList<TicketInfo> getGoupiaoren() {
        return goupiaoren;
    }

    public void setGoupiaoren(ArrayList<TicketInfo> goupiaoren) {
        this.goupiaoren = goupiaoren;
    }

    public String getSeatType() {
        return SeatType;
    }

    public void setSeatType(String seatType) {
        SeatType = seatType;
    }

    public String getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(String sumPrice) {
        SumPrice = sumPrice;
    }


}
