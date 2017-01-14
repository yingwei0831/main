package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/13.
 */
public class PlaneTicketInquiryRequest extends BasicFetchModel {

    /**
     * hangduan : [{"chuafachengshi":"","daodachengshi":"","chufariqi":""}]
     * chengren :
     * ertong :
     * cangwei :
     * qita :
     * lianxiren :
     * shouji :
     * dianhua :
     * youxiang :
     */

    private String chengren;
    private String ertong;
    private String cangwei;
    private String qita;
    private String lianxiren;
    private String shouji;
    private String dianhua;
    private String youxiang;
    private List<HangduanBean> hangduan;

    public PlaneTicketInquiryRequest(String chengren, String ertong, String cangwei, String qita, String lianxiren, String shouji, String dianhua, String youxiang) {
        this.chengren = chengren;
        this.ertong = ertong;
        this.cangwei = cangwei;
        this.qita = qita;
        this.lianxiren = lianxiren;
        this.shouji = shouji;
        this.dianhua = dianhua;
        this.youxiang = youxiang;
    }

    public String getChengren() {
        return chengren;
    }

    public void setChengren(String chengren) {
        this.chengren = chengren;
    }

    public String getErtong() {
        return ertong;
    }

    public void setErtong(String ertong) {
        this.ertong = ertong;
    }

    public String getCangwei() {
        return cangwei;
    }

    public void setCangwei(String cangwei) {
        this.cangwei = cangwei;
    }

    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }

    public String getLianxiren() {
        return lianxiren;
    }

    public void setLianxiren(String lianxiren) {
        this.lianxiren = lianxiren;
    }

    public String getShouji() {
        return shouji;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getDianhua() {
        return dianhua;
    }

    public void setDianhua(String dianhua) {
        this.dianhua = dianhua;
    }

    public String getYouxiang() {
        return youxiang;
    }

    public void setYouxiang(String youxiang) {
        this.youxiang = youxiang;
    }

    public List<HangduanBean> getHangduan() {
        return hangduan;
    }

    public void setHangduan(List<HangduanBean> hangduan) {
        this.hangduan = hangduan;
    }

    public static class HangduanBean extends BasicFetchModel{
        /**
         * chuafachengshi :
         * daodachengshi :
         * chufariqi :
         */

        private String chuafachengshi;
        private String daodachengshi;
        private String chufariqi;

        public HangduanBean(String chuafachengshi, String daodachengshi, String chufariqi) {
            this.chuafachengshi = chuafachengshi;
            this.daodachengshi = daodachengshi;
            this.chufariqi = chufariqi;
        }

        public String getChuafachengshi() {
            return chuafachengshi;
        }

        public void setChuafachengshi(String chuafachengshi) {
            this.chuafachengshi = chuafachengshi;
        }

        public String getDaodachengshi() {
            return daodachengshi;
        }

        public void setDaodachengshi(String daodachengshi) {
            this.daodachengshi = daodachengshi;
        }

        public String getChufariqi() {
            return chufariqi;
        }

        public void setChufariqi(String chufariqi) {
            this.chufariqi = chufariqi;
        }
    }
}
