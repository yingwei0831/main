package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by birney1 on 2017/1/3.
 * 火车票退票
 */
public class TrainOrderRefundRequest extends BasicFetchModel {

    /**
     * OrderNo : 7010813090026434
     * PlatOrderNo : EXHC170108130952252
     * RefundType : part                    订单退票类型  part：部分车票，all：整个订单内车票  默认为 all
     * tuipiaoren : [{"TktType":"1","PsgName":"姓名","CardType":"2","CardNo":"身份证号"}]
     * Reason :
     */

    private String OrderNo;
    private String PlatOrderNo;
    private String RefundType;
    private String Reason;
    private List<TuipiaorenBean> tuipiaoren;

    public TrainOrderRefundRequest(String orderNo, String platOrderNo, String refundType, String reason) {
        OrderNo = orderNo;
        PlatOrderNo = platOrderNo;
        RefundType = refundType;
        Reason = reason;
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

    public String getRefundType() {
        return RefundType;
    }

    public void setRefundType(String RefundType) {
        this.RefundType = RefundType;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String Reason) {
        this.Reason = Reason;
    }

    public List<TuipiaorenBean> getTuipiaoren() {
        return tuipiaoren;
    }

    public void setTuipiaoren(List<TuipiaorenBean> tuipiaoren) {
        this.tuipiaoren = tuipiaoren;
    }

    public static class TuipiaorenBean extends BasicFetchModel{
        /**
         * TktType : 1          0 儿童 1 成人
         * PsgName : 姓名
         * CardType : 2         1、一代身份证、 2、二代身份证、 3、港澳通行证、 4、台湾通行证、 5、护照
         * CardNo : 身份证号
         */

        private String TktType;
        private String PsgName;
        private String CardType;
        private String CardNo;

        public TuipiaorenBean(String tktType, String psgName, String cardType, String cardNo) {
            TktType = tktType;
            PsgName = psgName;
            CardType = cardType;
            CardNo = cardNo;
        }

        public String getTktType() {
            return TktType;
        }

        public void setTktType(String TktType) {
            this.TktType = TktType;
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
    }

    @Override
    public String toString() {
        return "TrainOrderRefundRequest{" +
                "OrderNo='" + OrderNo + '\'' +
                ", PlatOrderNo='" + PlatOrderNo + '\'' +
                ", RefundType='" + RefundType + '\'' +
                ", Reason='" + Reason + '\'' +
                ", tuipiaoren=" + tuipiaoren +
                '}';
    }
}
