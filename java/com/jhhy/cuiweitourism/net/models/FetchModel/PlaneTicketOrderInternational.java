package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2016/12/1.
 * 国际机票下单请求
 */
public class PlaneTicketOrderInternational extends BasicFetchModel{
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
    private List<List<InterFlightsBean>> InterFlights; //单程1条，往返2条
    private List<PassengersBean> Passengers;

    public PlaneTicketOrderInternational(String uid, String linkman, String linktel, String policyId, String platCode, String accountLevel, String balanceMoney, String platformType, String travelType, List<List<InterFlightsBean>> interFlights, List<PassengersBean> passengers) {
        this.uid = uid;
        this.linkman = linkman;
        this.linktel = linktel;
        PolicyId = policyId;
        PlatCode = platCode;
        AccountLevel = accountLevel;
        BalanceMoney = balanceMoney;
        PlatformType = platformType;
        TravelType = travelType;
        InterFlights = interFlights;
        Passengers = passengers;
    }

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

    public List<PassengersBean> getPassengers() {
        return Passengers;
    }

    public void setPassengers(List<PassengersBean> Passengers) {
        this.Passengers = Passengers;
    }

    public static class InterFlightsBean extends BasicFetchModel{
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
    }

    public static class PassengersBean extends BasicFetchModel{
        /**
         * Name : ba/wang
         * PsgType : 1          //1成人，2儿童   必填
         * CardType : 2         //2因私护照     必填
         * CardNo : E1301230122
         * MobilePhone : 13264349337
         * Fare : 1100          //票面价格
         * ShouldPay : 1023.0
         * Sex : M
         * BirthDay : 1980-03-07
         * Country : CN
         * TaxAmount : 428
         */

        private String Name;
        private String PsgType;
        private String CardType;
        private String CardNo;
        private String MobilePhone;
        private String Fare;
        private String ShouldPay;
        private String Sex;
        private String BirthDay;
        private String Country;
        private String TaxAmount;

        public PassengersBean(String name, String psgType, String cardType, String cardNo, String mobilePhone, String fare, String shouldPay, String sex, String birthDay, String country, String taxAmount) {
            Name = name;
            PsgType = psgType;
            CardType = cardType;
            CardNo = cardNo;
            MobilePhone = mobilePhone;
            Fare = fare;
            ShouldPay = shouldPay;
            Sex = sex;
            BirthDay = birthDay;
            Country = country;
            TaxAmount = taxAmount;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPsgType() {
            return PsgType;
        }

        public void setPsgType(String PsgType) {
            this.PsgType = PsgType;
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

        public String getMobilePhone() {
            return MobilePhone;
        }

        public void setMobilePhone(String MobilePhone) {
            this.MobilePhone = MobilePhone;
        }

        public String getFare() {
            return Fare;
        }

        public void setFare(String Fare) {
            this.Fare = Fare;
        }

        public String getShouldPay() {
            return ShouldPay;
        }

        public void setShouldPay(String ShouldPay) {
            this.ShouldPay = ShouldPay;
        }

        public String getSex() {
            return Sex;
        }

        public void setSex(String Sex) {
            this.Sex = Sex;
        }

        public String getBirthDay() {
            return BirthDay;
        }

        public void setBirthDay(String BirthDay) {
            this.BirthDay = BirthDay;
        }

        public String getCountry() {
            return Country;
        }

        public void setCountry(String Country) {
            this.Country = Country;
        }

        public String getTaxAmount() {
            return TaxAmount;
        }

        public void setTaxAmount(String TaxAmount) {
            this.TaxAmount = TaxAmount;
        }
    }

// {"head":{"code":"Order_gjflyorder"}, "field":{
// "PolicyId":"1",
// "PlatCode":"074",
// "AccountLevel":"10",
// "BalanceMoney":"2808",
// "CommitKey":"<![CDATA[QlpoOTFBWSZTWVmq%2BGgAFl1\/\/cAAABhAf\/\/\/P\/\/\/67\/v\r\n\/\/QQAEIAFAAIAAAAgEABAOAMv3ewU2ytAMVKUVBVEAAAAAAAA4yYJoZDIyMmhoA0GR\r\nhANBo0yGIaAHGTBNDIZGRk0NAGgyMIBoNGmQxDQAZJoppkJoyaM0QwTTI0GRkGQDC\r\nMgyBoAlPUkIyARiNTKeU\/UMU\/JJ6bSm0npPUB6gGmQAAOMmCaGQyMjJoaANBkYQDQa\r\nNMhiGgAVJCCBNNEyam0aNJiZqanohhRsgTENlB5QGnlLn9Tt9x5FPjK8D5fb3Gz5M\/YVNh\r\nc\/qZn8z1tuWbTJKJA5VVLTztkUeaZSFVwqIYjWHeQsD44xJG6K5ggiS29fQLtrUX%2Bw35\r\nkmJM6tCZkmaB7k3XRdHviSRs9TsukhakJ73t09K0stWt735VPLU8Ji3FiNFRW8MeE66XnU\r\n6mWVVlkeFnwuz1jcrUsVUsmyyLsS0tHNFr5lYr%2B%2B59Wcz1kWtu3ZzgcMpNrKRZeyt\r\n0S\/pql8YmOe50XrK5uttyhRSNKYs\/b8\/434uGmKD7d5Z4FOnloUb448fFlivE6Kr4HB2E2H\r\nCYZLbzMXWBziEiKu9JrynbnmnOePn79nyp0ZstCom1zy0di2TI3zK%2By%2B\/m2uWSzQ\r\nzM7aK44a5VWCsnq1sbXX243R4IGi5F6OmJth59FAhD0hWqVDAEURjXcjFKOQzZSvyRpvj\r\nbVsyYtWwZkuxYFxSS2umcOrPevWY%2BiKJafDGbm1ts2Z9%2BOPPVWMCps2Fk\/A6zKY\r\nZqaHRN6XMjoqVjlRVdemqtxya192kfo9qdSaTRUac3iz%2BFcFgkdNIiPqjtn2JSieibrcl3Hg\r\ngKVCIycDop9teOtZ0aVKsa4dHZv7czgzGutVhrgSxqs6Fss8m%2B\/IcXkShFZKjujEEkgUI\r\nZpNIBPBCNpDqhD4191\/El19KYhfFhZipHLis89TTM%2Bql0pPvxaF6PwWttVvta7m%2Bot\r\nMGuuDC\/AqzTRRbSu8VLZaW1UTCT0pytOVNm%2B29vxWpkZGwvatm6Sy2pqZZY3Yhj\r\nUaFaDA5rnAyOGkWimZtXq%2Bq95fMysytv5li\/35NMDW2n3ZcyTLfwtWN4rZsWtZjO\/C2j\r\nOpUS2GymTiyy04XzalyxdijdGVKqjPSwxNmu6%2B1nbhqktLqila0q62ha2s22vksUturbDF\r\nt1VjRW%2BBhSJubBDIBeJvbjvO3tBDq7zuMRxxSiqoitob0CiL%2BfjGsrFVYEBBBBBDk6d\r\nMmzr9WGPXJIIxMkg9x5eQwGGHiS1rFFiqKKV2dvPw%2Bl8Lcf5\/sLdCbDluN4UOWCgMF\r\nAYZVdZDMhkDhVREKoDCFQyOeWlVRYuUjvNT0RKKiYz27bpVKuMhZQuLjFW93g6Lf4kid\/\r\nv\/g6DKCeNFPZvqqoIRvkVGcyBkBBCoC5gPi4MjM\/Ucydh3exPt%2Bsy7D2a\/G8h2vMcOa\r\nKKqui1cs3cRUwmRKT3s1rqqqUtB6y0oFUDpAQkOA1eJVVRqqhFVaFlnuPF9NVwdr7NDQy\r\n%2BB88d9yko5Oj1w\/2LqFFlJTtqMTcWtgtChcxjIyGUuuLTuO45jjx43z21aSpaVRsF116VjJ\r\nVVAQFRFXq18oRoDRB2sa0UwHOGaTDDe3JNy1Er7PI8vWY75YuWVUvyLohuCDX5Y5iPA\r\nNwGoD9OEMuZaplcB1B5NsaXRF\/rK4itRG7rG5CSylGmICog8x4tnM7Zb9mocwBsD2sags\r\n0LyGskKAgh3yBIC2zNf2%2BYwWGYz5y\/bznqW%2BGY9JIkkmgvXlVVURwJJxEygICJ5%\r\n2B6DXYZG4iMWcaW24SSYa1UsWUpYdj8Tmyy6nVveUse\/0LTgfC0sYmukRrbLS4VUTIIS\r\nAgghIESSZcwve\/jqrlziafW\/cfttU%2Bbw083dHyHP8l6yTgkpISBBE3ATK1sqcUu8AbxSKo\r\nVayqrf6KEsedpRZlprJIt2%2BjGZe4xEGMiEN1lgcfSRHkSCp2ngao%2BD5ks9tKcvEdXx8x\r\nB2HCRcRzJEChHpJhO4uP4HSD8He2bx\/d%2B6FPoJOxPp9wqkpoyiRY3A5QnrQpzk6EHrj\r\nBqGw4nPHzJJsT2EXUVHOsPmeI%2Bo6zUc4Zo2v6D8Ts0V7zx5j6lD3myylIpSKZhZJZ%2\r\nBtteUwsqJ1POfyOZsRhFFJT6377N7VHvQZIzPMbkwuZyacdtQpTyt9y1ldNnpXl7sbmVlaV1\r\nDJemHArpyXZ7FS%2BdqzSU000ZmeL3NmxsZqxnV19VtBZlphgwylpsoqlpRZbTYRoUjM\r\nWihZFCpKqUqqr0K1U2mSTIoKjbVVhPGnd1IU\/wZqJuNxm72g2p52riWG0HA50c7nc6k5n\r\nKFEav1jihzCeOPxFFFRRUb02RT6F3JTgGRvkpMzjJzG4OYLvNzGCocx8rvHgD1D0w8zuH2\r\nmCzyJD9Jk9C6d6UoSpIeMKFidSeJeXoqlKssyF5JZMiRZ9%2BiT0uxKe1KTaIp5yzyhvJOK\r\nkp0xO5F5IeVEU4noMMgofJGx3hqlnU3m92lRKQpVVUOekVKUo4TeOoME3Np53akOtOhf\r\nxBkohtiHiqqkk8QtE5xOWzB9PX2VUpcHQqLHytyDI6TIzMxYzEe11p2GrrSckmhNslEmr5z\r\nndBvBmGwaShU6U9QyOkahifWWDnUdClrVVr7Boi6nnXl5SpTCgwjcUpi6OpkkLSRN6M4U\r\nmx\/JP33anebYk0FIopQ2KWCy56O2R7KktRagy4oOlqKfBtEs%2BQO5nOg6Ejdg8BO4cDy\r\nIpofEHxJk8xo3RDYUHAe2T3LB3JOPr6h0i7qQ8Z4EdyGBsD4HfoJl6BsLwjaVJPJQWHlGEW\r\nk7lIolCy0Da7Cx8yC8kbUOzVcGIyBoWPK9YsHOG5nJHqBTgahZDduYkwlw3LKP09XiOW\r\nm8p7iWWmDGCYT42ShLoos2kw7CZQxJwYLpaSJcuIpB6MpSS%2BXmuLHAosUZVJZUy\r\nqSYJlly%2BIs0GwLmr4IwLLBtuKWbS6ikoXwqPMbGsE9LMLssChRGdTRwNRZ5\/0WDcTU\r\n4obEidh7l5zknafGfOMpOLk9gdskk40OR2C0nSh0vPLtp6OiIcpOlzDqaBZrZJYd6ZDwLu3g\r\nSqLMWvYi1S1DZL3oWlrEslKUWVCwso63oIzHs9X7PF1fcec4P9ZVVSSH\/VSQkjyFRCH%2\r\nBZUhJPzKkif0pJJCH5n2FiQdBSEif%2BFH\/pQIJ\/YqQjzFRCRPyKiJA\/KoIiTb%2BVm8r\/7%\r\n2Bx%2BfI\/Myj4\/oPzH\/4u5IpwoSCzVfDQA]]>",
// "TravelType":"RT",
// "InterFlights":[
//   [
//     {"SegmentID":"0","SegmentType":"S1","TicketingCarrier":"NX","ArrivalDate":"2015-11-16","ArrivalTime":"15:55","BoardPoint":"PEK","BoardPointAT":"T3",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-16","DepartureTime":"12:10","FlightNo":"005","OffPoint":"MFM","OffPointAT":""},
//     {"SegmentID":"1","SegmentType":"S1","TicketingCarrier":"NX","ArrivalDate":"2015-11-16","ArrivalTime":"19:55","BoardPoint":"MFM","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-16","DepartureTime":"18:10","FlightNo":"882","OffPoint":"BKK","OffPointAT":""}
//   ],
//   [
//     {"SegmentID":"0","SegmentType":"S2","TicketingCarrier":"NX","ArrivalDate":"2015-11-18","ArrivalTime":"06:30","BoardPoint":"BKK","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-18","DepartureTime":"02:55","FlightNo":"879","OffPoint":"MFM","OffPointAT":""},
//     {"SegmentID":"1","SegmentType":"S2","TicketingCarrier":"NX","ArrivalDate":"2015-11-18","ArrivalTime":"10:10","BoardPoint":"MFM","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-18","DepartureTime":"07:10","FlightNo":"006","OffPoint":"PEK","OffPointAT":"T3"}
//   ]
// ],
// "Passengers":[
//   {"Name":"zhiyong","PsgType":"1","CardType":"2","CardNo":"E1301230122","MobilePhone":"13264349337",
//    "Fare":"2030","ShouldPay":"738","Sex":"M","BirthDay":"1980-03-07","Country":"CN"}
// ]}}



}
