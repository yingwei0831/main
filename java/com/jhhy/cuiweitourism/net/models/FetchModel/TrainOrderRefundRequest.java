package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by birney1 on 2017/1/3.
 * 火车票退票
 */
public class TrainOrderRefundRequest extends BasicFetchModel {

    /**
     * OrderNo :
     * PlatOrderNo :
     * RefundType : part
     * tuipiaoren : [{"TktType":"","PsgName":"","CardType":"","CardNo":""},{"TktType":"","PsgName":"","CardType":"","CardNo":""}]
     * Reason :
     */

    private String OrderNo;
    private String PlatOrderNo;
    private String RefundType;
    private String Reason;
    /**
     * TktType :
     * PsgName :
     * CardType :
     * CardNo :
     */

    private List<TuipiaorenBean> tuipiaoren;

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
        private String TktType;
        private String PsgName;
        private String CardType;
        private String CardNo;

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
}
