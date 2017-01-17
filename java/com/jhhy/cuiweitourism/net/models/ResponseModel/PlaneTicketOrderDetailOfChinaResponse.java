package com.jhhy.cuiweitourism.net.models.ResponseModel;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/9.
 * 国内机票订单详情
 */
public class PlaneTicketOrderDetailOfChinaResponse {

    /**
     * return : {"returnCode":"S","policyOrder":{"flightInfoList":{"arrCode":"DLC","arrTime":"2135","depCode":"PEK","depDate":"2017-02-20","depTime":"2010","flightNo":"MU2312","planeType":"","seatClass":"F","seatDiscount":3},"liantuoOrderNo":"112017010683329278","passengerList":{"airportTax":50,"fuelTax":0,"identityNo":"230707198810060411","identityType":"1","name":"张金龙","parPrice":2130,"passengerType":"ADULT","refundStatus":1,"settlePrice":2130},"paymentInfo":{"paymentUrl":"https://mapi.alipay.com/gateway.do?body=%E6%9C%BA%E7%A5%A8%E9%87%87%E8%B4%AD++JPBPFZ%E8%88%AA%E7%8F%AD%3AMU2312+%E8%88%AA%E7%A8%8B%3A+PEK-DLC%E4%B9%98%E6%9C%BA%E4%BA%BA%3A%E5%BC%A0%E9%87%91%E9%BE%99%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A112017010683329278%29%E6%93%8D%E4%BD%9C%E5%91%98%3A%E5%8C%97%E9%9D%92%E6%97%85%E8%A1%8C%E7%A4%BE%E9%87%87%E8%B4%AD%EF%BC%9ABQLXS+%E5%87%BA%E7%A5%A8%E4%B8%AD%E5%BF%83%E8%AE%A2%E5%8D%95%E5%8F%B7%EF%BC%9A&amp;subject=%E6%9C%BA%E7%A5%A8&amp;notify_url=http%3A%2F%2Fpay.51ebill.com%2Fpayment%2Fpay%2FalipayPaynotify_1.in%3FlogId%3D215047315&amp;out_trade_no=112017010683329278&amp;return_url=http%3A%2F%2Falipay.solaridc.com%3A8000%2Fliantuo%2Fmanage%2FpolicyOrderPaidReturn.in&amp;credit_card_pay=Y&amp;_input_charset=utf-8&amp;total_fee=2130.0&amp;credit_card_default_display=Y&amp;service=create_direct_pay_by_user&amp;paymethod=directPay&amp;partner=2088201994709221&amp;seller_email=zfblt%40126.com&amp;payment_type=1&amp;sign=2e1f9b6ada9cddf540536ad725ee2244&amp;sign_type=MD5","totalAirportTax":50,"totalFuelTax":0,"totalParPrice":2130,"totalPay":2130},"pnrNo":"JPBPFZ","pnrTxt":"1.张金龙 JPBPFZr 2.  MU2312 F   MO20FEB  PEKDLC HK1   2010 2135          E T2-- r 3.SHA/T SHA/T 021-23079160/SHANGHAI GUANGFA AIR-TICKET SERVICE CO. LTD/r    /ZHAOHONG ABCDEFG   r 4.TL/0000/20FEB/SHA666 r 5.SSR FOID MU HK1 NI230707198810060411/P1  r 6.SSR CKIN MU  r 7.SSR FQTV MU HK1 PEKDLC 2312 F20FEB MU610501142781/P1 r 8.SSR ADTK 1E BY SHA09JAN17/1648 OR CXL MU2312 F20FEB  r 9.OSI MU CTCM15755163603/P1r10.OSI MU CTCT13910144456   r11.RMK TJ AUTH CKG141                                                          + 12.RMK CA/PBD1XV                                                               -13.SHA666","policyData":{"comment":"rn改期、升舱即收回代理费&lt;/br&gt;","commisionMoney":40,"commisionPoint":"0.5","commisionType":0,"needSwitchPNR":0,"policyBelongTo":"2","policyId":187697896,"policyType":"BSP","seatType":"0","status":"1","vtWorkingTime":"09:00-22:59","workTime":"08:00-23:50"},"status":"NEW_ORDER"}}
     */

    @SerializedName("return")
    private ReturnBean returnX;

    public ReturnBean getReturnX() {
        return returnX;
    }

    public void setReturnX(ReturnBean returnX) {
        this.returnX = returnX;
    }

    public static class ReturnBean {
        /**
         * returnCode : S
         * policyOrder : {"flightInfoList":{"arrCode":"DLC","arrTime":"2135","depCode":"PEK","depDate":"2017-02-20","depTime":"2010","flightNo":"MU2312","planeType":"","seatClass":"F","seatDiscount":3},"liantuoOrderNo":"112017010683329278","passengerList":{"airportTax":50,"fuelTax":0,"identityNo":"230707198810060411","identityType":"1","name":"张金龙","parPrice":2130,"passengerType":"ADULT","refundStatus":1,"settlePrice":2130},"paymentInfo":{"paymentUrl":"https://mapi.alipay.com/gateway.do?body=%E6%9C%BA%E7%A5%A8%E9%87%87%E8%B4%AD++JPBPFZ%E8%88%AA%E7%8F%AD%3AMU2312+%E8%88%AA%E7%A8%8B%3A+PEK-DLC%E4%B9%98%E6%9C%BA%E4%BA%BA%3A%E5%BC%A0%E9%87%91%E9%BE%99%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A112017010683329278%29%E6%93%8D%E4%BD%9C%E5%91%98%3A%E5%8C%97%E9%9D%92%E6%97%85%E8%A1%8C%E7%A4%BE%E9%87%87%E8%B4%AD%EF%BC%9ABQLXS+%E5%87%BA%E7%A5%A8%E4%B8%AD%E5%BF%83%E8%AE%A2%E5%8D%95%E5%8F%B7%EF%BC%9A&amp;subject=%E6%9C%BA%E7%A5%A8&amp;notify_url=http%3A%2F%2Fpay.51ebill.com%2Fpayment%2Fpay%2FalipayPaynotify_1.in%3FlogId%3D215047315&amp;out_trade_no=112017010683329278&amp;return_url=http%3A%2F%2Falipay.solaridc.com%3A8000%2Fliantuo%2Fmanage%2FpolicyOrderPaidReturn.in&amp;credit_card_pay=Y&amp;_input_charset=utf-8&amp;total_fee=2130.0&amp;credit_card_default_display=Y&amp;service=create_direct_pay_by_user&amp;paymethod=directPay&amp;partner=2088201994709221&amp;seller_email=zfblt%40126.com&amp;payment_type=1&amp;sign=2e1f9b6ada9cddf540536ad725ee2244&amp;sign_type=MD5","totalAirportTax":50,"totalFuelTax":0,"totalParPrice":2130,"totalPay":2130},"pnrNo":"JPBPFZ","pnrTxt":"1.张金龙 JPBPFZr 2.  MU2312 F   MO20FEB  PEKDLC HK1   2010 2135          E T2-- r 3.SHA/T SHA/T 021-23079160/SHANGHAI GUANGFA AIR-TICKET SERVICE CO. LTD/r    /ZHAOHONG ABCDEFG   r 4.TL/0000/20FEB/SHA666 r 5.SSR FOID MU HK1 NI230707198810060411/P1  r 6.SSR CKIN MU  r 7.SSR FQTV MU HK1 PEKDLC 2312 F20FEB MU610501142781/P1 r 8.SSR ADTK 1E BY SHA09JAN17/1648 OR CXL MU2312 F20FEB  r 9.OSI MU CTCM15755163603/P1r10.OSI MU CTCT13910144456   r11.RMK TJ AUTH CKG141                                                          + 12.RMK CA/PBD1XV                                                               -13.SHA666","policyData":{"comment":"rn改期、升舱即收回代理费&lt;/br&gt;","commisionMoney":40,"commisionPoint":"0.5","commisionType":0,"needSwitchPNR":0,"policyBelongTo":"2","policyId":187697896,"policyType":"BSP","seatType":"0","status":"1","vtWorkingTime":"09:00-22:59","workTime":"08:00-23:50"},"status":"NEW_ORDER"}
         */

        private String returnCode;
        private PolicyOrderBean policyOrder;

        public String getReturnCode() {
            return returnCode;
        }

        public void setReturnCode(String returnCode) {
            this.returnCode = returnCode;
        }

        public PolicyOrderBean getPolicyOrder() {
            return policyOrder;
        }

        public void setPolicyOrder(PolicyOrderBean policyOrder) {
            this.policyOrder = policyOrder;
        }

        @Override
        public String toString() {
            return "ReturnBean{" +
                    "returnCode='" + returnCode + '\'' +
                    ", policyOrder=" + policyOrder +
                    '}';
        }
    }
    public static class PolicyOrderBean {
        /**
         * flightInfoList : {"arrCode":"DLC","arrTime":"2135","depCode":"PEK","depDate":"2017-02-20","depTime":"2010","flightNo":"MU2312","planeType":"","seatClass":"F","seatDiscount":3}
         * liantuoOrderNo : 112017010683329278
         * passengerList : {"airportTax":50,"fuelTax":0,"identityNo":"230707198810060411","identityType":"1","name":"张金龙","parPrice":2130,"passengerType":"ADULT","refundStatus":1,"settlePrice":2130}
         * paymentInfo : {"paymentUrl":"https://mapi.alipay.com/gateway.do?body=%E6%9C%BA%E7%A5%A8%E9%87%87%E8%B4%AD++JPBPFZ%E8%88%AA%E7%8F%AD%3AMU2312+%E8%88%AA%E7%A8%8B%3A+PEK-DLC%E4%B9%98%E6%9C%BA%E4%BA%BA%3A%E5%BC%A0%E9%87%91%E9%BE%99%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A112017010683329278%29%E6%93%8D%E4%BD%9C%E5%91%98%3A%E5%8C%97%E9%9D%92%E6%97%85%E8%A1%8C%E7%A4%BE%E9%87%87%E8%B4%AD%EF%BC%9ABQLXS+%E5%87%BA%E7%A5%A8%E4%B8%AD%E5%BF%83%E8%AE%A2%E5%8D%95%E5%8F%B7%EF%BC%9A&amp;subject=%E6%9C%BA%E7%A5%A8&amp;notify_url=http%3A%2F%2Fpay.51ebill.com%2Fpayment%2Fpay%2FalipayPaynotify_1.in%3FlogId%3D215047315&amp;out_trade_no=112017010683329278&amp;return_url=http%3A%2F%2Falipay.solaridc.com%3A8000%2Fliantuo%2Fmanage%2FpolicyOrderPaidReturn.in&amp;credit_card_pay=Y&amp;_input_charset=utf-8&amp;total_fee=2130.0&amp;credit_card_default_display=Y&amp;service=create_direct_pay_by_user&amp;paymethod=directPay&amp;partner=2088201994709221&amp;seller_email=zfblt%40126.com&amp;payment_type=1&amp;sign=2e1f9b6ada9cddf540536ad725ee2244&amp;sign_type=MD5","totalAirportTax":50,"totalFuelTax":0,"totalParPrice":2130,"totalPay":2130}
         * pnrNo : JPBPFZ
         * pnrTxt : 1.张金龙 JPBPFZr 2.  MU2312 F   MO20FEB  PEKDLC HK1   2010 2135          E T2-- r 3.SHA/T SHA/T 021-23079160/SHANGHAI GUANGFA AIR-TICKET SERVICE CO. LTD/r    /ZHAOHONG ABCDEFG   r 4.TL/0000/20FEB/SHA666 r 5.SSR FOID MU HK1 NI230707198810060411/P1  r 6.SSR CKIN MU  r 7.SSR FQTV MU HK1 PEKDLC 2312 F20FEB MU610501142781/P1 r 8.SSR ADTK 1E BY SHA09JAN17/1648 OR CXL MU2312 F20FEB  r 9.OSI MU CTCM15755163603/P1r10.OSI MU CTCT13910144456   r11.RMK TJ AUTH CKG141                                                          + 12.RMK CA/PBD1XV                                                               -13.SHA666
         * policyData : {"comment":"rn改期、升舱即收回代理费&lt;/br&gt;","commisionMoney":40,"commisionPoint":"0.5","commisionType":0,"needSwitchPNR":0,"policyBelongTo":"2","policyId":187697896,"policyType":"BSP","seatType":"0","status":"1","vtWorkingTime":"09:00-22:59","workTime":"08:00-23:50"}
         * status : NEW_ORDER
         */

        private String liantuoOrderNo;
        private PaymentInfoBean paymentInfo;
        private String pnrNo;
        private String pnrTxt;
        private PolicyDataBean policyData;
        private String status;
        private List<FlightInfoListBean> flightInfoList;
        private List<PassengerListBean> passengerList;

        public List<FlightInfoListBean> getFlightInfoList() {
            return flightInfoList;
        }

        public void setFlightInfoList(List<FlightInfoListBean> flightInfoList) {
            this.flightInfoList = flightInfoList;
        }

        public String getLiantuoOrderNo() {
            return liantuoOrderNo;
        }

        public void setLiantuoOrderNo(String liantuoOrderNo) {
            this.liantuoOrderNo = liantuoOrderNo;
        }

        public List<PassengerListBean> getPassengerList() {
            return passengerList;
        }

        public void setPassengerList(List<PassengerListBean> passengerList) {
            this.passengerList = passengerList;
        }

        public PaymentInfoBean getPaymentInfo() {
            return paymentInfo;
        }

        public void setPaymentInfo(PaymentInfoBean paymentInfo) {
            this.paymentInfo = paymentInfo;
        }

        public String getPnrNo() {
            return pnrNo;
        }

        public void setPnrNo(String pnrNo) {
            this.pnrNo = pnrNo;
        }

        public String getPnrTxt() {
            return pnrTxt;
        }

        public void setPnrTxt(String pnrTxt) {
            this.pnrTxt = pnrTxt;
        }

        public PolicyDataBean getPolicyData() {
            return policyData;
        }

        public void setPolicyData(PolicyDataBean policyData) {
            this.policyData = policyData;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "PolicyOrderBean{" +
                    "flightInfoList=" + flightInfoList +
                    ", liantuoOrderNo='" + liantuoOrderNo + '\'' +
                    ", passengerList=" + passengerList +
                    ", paymentInfo=" + paymentInfo +
                    ", pnrNo='" + pnrNo + '\'' +
                    ", pnrTxt='" + pnrTxt + '\'' +
                    ", policyData=" + policyData +
                    ", status='" + status + '\'' +
                    '}';
        }
    }

    public static class FlightInfoListBean {
        /**
         * arrCode : DLC
         * arrTime : 2135
         * depCode : PEK
         * depDate : 2017-02-20
         * depTime : 2010
         * flightNo : MU2312
         * planeType :
         * seatClass : F
         * seatDiscount : 3
         * arrCodes: 大连周水子国际机场
         * depCodes: 北京首都国际机场
         */

        private String arrCode;
        private String arrTime;
        private String depCode;
        private String depDate;
        private String depTime;
        private String flightNo;
        private String planeType;
        private String seatClass;
        private double seatDiscount;    //舱位折扣
        private String arrCodes;
        private String depCodes;

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

        public String getPlaneType() {
            return planeType;
        }

        public void setPlaneType(String planeType) {
            this.planeType = planeType;
        }

        public String getSeatClass() {
            return seatClass;
        }

        public void setSeatClass(String seatClass) {
            this.seatClass = seatClass;
        }

        public double getSeatDiscount() {
            return seatDiscount;
        }

        public void setSeatDiscount(int seatDiscount) {
            this.seatDiscount = seatDiscount;
        }

        public String getArrCodes() {
            return arrCodes;
        }

        public void setArrCodes(String arrCodes) {
            this.arrCodes = arrCodes;
        }

        public String getDepCodes() {
            return depCodes;
        }

        public void setDepCodes(String depCodes) {
            this.depCodes = depCodes;
        }

        @Override
        public String toString() {
            return "FlightInfoListBean{" +
                    "arrCode='" + arrCode + '\'' +
                    ", arrTime='" + arrTime + '\'' +
                    ", depCode='" + depCode + '\'' +
                    ", depDate='" + depDate + '\'' +
                    ", depTime='" + depTime + '\'' +
                    ", flightNo='" + flightNo + '\'' +
                    ", planeType='" + planeType + '\'' +
                    ", seatClass='" + seatClass + '\'' +
                    ", seatDiscount=" + seatDiscount +
                    '}';
        }
    }

    public static class PassengerListBean {
        /**
         * airportTax : 50      //机建费
         * fuelTax : 0          //燃油费
         * identityNo : 230707198810060411      //证件号码
         * identityType : 1
         * name : 张金龙
         * parPrice : 2130
         * passengerType : ADULT
         * refundStatus : 1
         * settlePrice : 2130
         * ticketNo             //票号：781-8764777473
         */

        private double airportTax;
        private double fuelTax;
        private String identityNo;
        private String identityType;
        private String name;
        private double parPrice;        //票面价
        private String passengerType;
        private int refundStatus;       //退废票状态     1 有退废票  0 无退废票
        private double settlePrice;     //单人结算价

        private String ticketNo;        //781-8764777473
        private String tui;             //已退票

        public String getTicketNo() {
            return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
            this.ticketNo = ticketNo;
        }

        public double getAirportTax() {
            return airportTax;
        }

        public void setAirportTax(int airportTax) {
            this.airportTax = airportTax;
        }

        public double getFuelTax() {
            return fuelTax;
        }

        public void setFuelTax(int fuelTax) {
            this.fuelTax = fuelTax;
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

        public double getParPrice() {
            return parPrice;
        }

        public void setParPrice(int parPrice) {
            this.parPrice = parPrice;
        }

        public String getPassengerType() {
            return passengerType;
        }

        public void setPassengerType(String passengerType) {
            this.passengerType = passengerType;
        }

        public int getRefundStatus() {
            return refundStatus;
        }

        public void setRefundStatus(int refundStatus) {
            this.refundStatus = refundStatus;
        }

        public double getSettlePrice() {
            return settlePrice;
        }

        public void setSettlePrice(int settlePrice) {
            this.settlePrice = settlePrice;
        }

        public String getTui() {
            return tui;
        }

        public void setTui(String tui) {
            this.tui = tui;
        }

        @Override
        public String toString() {
            return "PassengerListBean{" +
                    "airportTax=" + airportTax +
                    ", fuelTax=" + fuelTax +
                    ", identityNo='" + identityNo + '\'' +
                    ", identityType='" + identityType + '\'' +
                    ", name='" + name + '\'' +
                    ", parPrice=" + parPrice +
                    ", passengerType='" + passengerType + '\'' +
                    ", refundStatus=" + refundStatus +
                    ", settlePrice=" + settlePrice +
                    ", ticketNo='" + ticketNo + '\'' +
                    ", tui='" + tui + '\'' +
                    '}';
        }
    }

    public static class PaymentInfoBean {
        /**
         * paymentUrl : https://mapi.alipay.com/gateway.do?body=%E6%9C%BA%E7%A5%A8%E9%87%87%E8%B4%AD++JPBPFZ%E8%88%AA%E7%8F%AD%3AMU2312+%E8%88%AA%E7%A8%8B%3A+PEK-DLC%E4%B9%98%E6%9C%BA%E4%BA%BA%3A%E5%BC%A0%E9%87%91%E9%BE%99%28%E8%AE%A2%E5%8D%95%E5%8F%B7%3A112017010683329278%29%E6%93%8D%E4%BD%9C%E5%91%98%3A%E5%8C%97%E9%9D%92%E6%97%85%E8%A1%8C%E7%A4%BE%E9%87%87%E8%B4%AD%EF%BC%9ABQLXS+%E5%87%BA%E7%A5%A8%E4%B8%AD%E5%BF%83%E8%AE%A2%E5%8D%95%E5%8F%B7%EF%BC%9A&amp;subject=%E6%9C%BA%E7%A5%A8&amp;notify_url=http%3A%2F%2Fpay.51ebill.com%2Fpayment%2Fpay%2FalipayPaynotify_1.in%3FlogId%3D215047315&amp;out_trade_no=112017010683329278&amp;return_url=http%3A%2F%2Falipay.solaridc.com%3A8000%2Fliantuo%2Fmanage%2FpolicyOrderPaidReturn.in&amp;credit_card_pay=Y&amp;_input_charset=utf-8&amp;total_fee=2130.0&amp;credit_card_default_display=Y&amp;service=create_direct_pay_by_user&amp;paymethod=directPay&amp;partner=2088201994709221&amp;seller_email=zfblt%40126.com&amp;payment_type=1&amp;sign=2e1f9b6ada9cddf540536ad725ee2244&amp;sign_type=MD5
         * totalAirportTax : 50     机建总价
         * totalFuelTax : 0
         * totalParPrice : 2130     票面总价
         * totalPay : 2130          总结算价
         */

        /**
         * "payTradeNo": "2017010921001004980282323109",    //交易号
         * "payerAccount": "cwly1118@126.com",              //支付账号
         * "totalAirportTax": 50,                           //机建费
         * "totalFuelTax": 0,                               //燃油费
         * "totalParPrice": 2130,                           //票面总价
         * "totalPay": 2143                                 //总价
         */

        private String payTradeNo;      //交易号
        private String payerAccount;    //付款账户

        private String paymentUrl;      //未支付的订单返回paymentUrl,已支付的订单返回空
        private double totalAirportTax;
        private double totalFuelTax;    //燃油总价
        private double totalParPrice;
        private double totalPay;

        public String getPayTradeNo() {
            return payTradeNo;
        }

        public void setPayTradeNo(String payTradeNo) {
            this.payTradeNo = payTradeNo;
        }

        public String getPayerAccount() {
            return payerAccount;
        }

        public void setPayerAccount(String payerAccount) {
            this.payerAccount = payerAccount;
        }

        public String getPaymentUrl() {
            return paymentUrl;
        }

        public void setPaymentUrl(String paymentUrl) {
            this.paymentUrl = paymentUrl;
        }

        public double getTotalAirportTax() {
            return totalAirportTax;
        }

        public void setTotalAirportTax(int totalAirportTax) {
            this.totalAirportTax = totalAirportTax;
        }

        public double getTotalFuelTax() {
            return totalFuelTax;
        }

        public void setTotalFuelTax(int totalFuelTax) {
            this.totalFuelTax = totalFuelTax;
        }

        public double getTotalParPrice() {
            return totalParPrice;
        }

        public void setTotalParPrice(int totalParPrice) {
            this.totalParPrice = totalParPrice;
        }

        public double getTotalPay() {
            return totalPay;
        }

        public void setTotalPay(int totalPay) {
            this.totalPay = totalPay;
        }

        @Override
        public String toString() {
            return "PaymentInfoBean{" +
                    "payTradeNo='" + payTradeNo + '\'' +
                    ", payerAccount='" + payerAccount + '\'' +
                    ", paymentUrl='" + paymentUrl + '\'' +
                    ", totalAirportTax=" + totalAirportTax +
                    ", totalFuelTax=" + totalFuelTax +
                    ", totalParPrice=" + totalParPrice +
                    ", totalPay=" + totalPay +
                    '}';
        }
    }

    public static class PolicyDataBean {
        /**
         * comment : rn改期、升舱即收回代理费&lt;/br&gt;
         * commisionMoney : 40
         * commisionPoint : 0.5
         * commisionType : 0
         * needSwitchPNR : 0
         * policyBelongTo : 2
         * policyId : 187697896
         * policyType : BSP
         * seatType : 0
         * status : 1
         * vtWorkingTime : 09:00-22:59
         * workTime : 08:00-23:50
         */

        private String comment;
        private float commisionMoney;
        private String commisionPoint;
        private int commisionType;
        private int needSwitchPNR;
        private String policyBelongTo;
        private int policyId;
        private String policyType;
        private String seatType;
        private String status;
        private String vtWorkingTime;
        private String workTime;

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public double getCommisionMoney() {
            return commisionMoney;
        }

        public void setCommisionMoney(int commisionMoney) {
            this.commisionMoney = commisionMoney;
        }

        public String getCommisionPoint() {
            return commisionPoint;
        }

        public void setCommisionPoint(String commisionPoint) {
            this.commisionPoint = commisionPoint;
        }

        public int getCommisionType() {
            return commisionType;
        }

        public void setCommisionType(int commisionType) {
            this.commisionType = commisionType;
        }

        public int getNeedSwitchPNR() {
            return needSwitchPNR;
        }

        public void setNeedSwitchPNR(int needSwitchPNR) {
            this.needSwitchPNR = needSwitchPNR;
        }

        public String getPolicyBelongTo() {
            return policyBelongTo;
        }

        public void setPolicyBelongTo(String policyBelongTo) {
            this.policyBelongTo = policyBelongTo;
        }

        public int getPolicyId() {
            return policyId;
        }

        public void setPolicyId(int policyId) {
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getVtWorkingTime() {
            return vtWorkingTime;
        }

        public void setVtWorkingTime(String vtWorkingTime) {
            this.vtWorkingTime = vtWorkingTime;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        @Override
        public String toString() {
            return "PolicyDataBean{" +
                    "comment='" + comment + '\'' +
                    ", commisionMoney=" + commisionMoney +
                    ", commisionPoint='" + commisionPoint + '\'' +
                    ", commisionType=" + commisionType +
                    ", needSwitchPNR=" + needSwitchPNR +
                    ", policyBelongTo='" + policyBelongTo + '\'' +
                    ", policyId=" + policyId +
                    ", policyType='" + policyType + '\'' +
                    ", seatType='" + seatType + '\'' +
                    ", status='" + status + '\'' +
                    ", vtWorkingTime='" + vtWorkingTime + '\'' +
                    ", workTime='" + workTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "PlaneTicketOrderDetailOfChinaResponse{" +
                "returnX=" + returnX +
                '}';
    }
}
