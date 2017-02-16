package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/16.
 */
public class InsuranceOrderRequest extends BasicFetchModel {

    /**
     * memberid : 888
     * id : 1
     * productname : 国内游10元套餐
     * price : 10
     * usedate : 2017-02-19
     * dingnum : 2
     * jinjlxr : 张三
     * jinjitel : 1521656884
     * beibr : [{"tourername":"***","cardnumber":"25596878954521659","mobile":"15698459875"}]
     * remark : 就不上，不怕死
     * usejifen : 1
     * needjifen : 9
     */

    private String memberid;
    private String id;
    private String productname;
    private String price;
    private String usedate;
    private String dingnum;
    private String jinjlxr;
    private String jinjitel;
    private String remark;
    private String usejifen;
    private String needjifen;
    private List<BeibrBean> beibr;

    public InsuranceOrderRequest(String memberid, String id, String productname, String price, String usedate, String dingnum, String jinjlxr, String jinjitel, String remark, String usejifen, String needjifen) {
        this.memberid = memberid;
        this.id = id;
        this.productname = productname;
        this.price = price;
        this.usedate = usedate;
        this.dingnum = dingnum;
        this.jinjlxr = jinjlxr;
        this.jinjitel = jinjitel;
        this.remark = remark;
        this.usejifen = usejifen;
        this.needjifen = needjifen;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getUsedate() {
        return usedate;
    }

    public void setUsedate(String usedate) {
        this.usedate = usedate;
    }

    public String getDingnum() {
        return dingnum;
    }

    public void setDingnum(String dingnum) {
        this.dingnum = dingnum;
    }

    public String getJinjlxr() {
        return jinjlxr;
    }

    public void setJinjlxr(String jinjlxr) {
        this.jinjlxr = jinjlxr;
    }

    public String getJinjitel() {
        return jinjitel;
    }

    public void setJinjitel(String jinjitel) {
        this.jinjitel = jinjitel;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsejifen() {
        return usejifen;
    }

    public void setUsejifen(String usejifen) {
        this.usejifen = usejifen;
    }

    public String getNeedjifen() {
        return needjifen;
    }

    public void setNeedjifen(String needjifen) {
        this.needjifen = needjifen;
    }

    public List<BeibrBean> getBeibr() {
        return beibr;
    }

    public void setBeibr(List<BeibrBean> beibr) {
        this.beibr = beibr;
    }

    public static class BeibrBean extends BasicFetchModel {
        /**
         * tourername : ***
         * cardnumber : 25596878954521659
         * mobile : 15698459875
         */

        private String tourername;
        private String cardnumber;
        private String mobile;

        public BeibrBean(String tourername, String cardnumber, String mobile) {
            this.tourername = tourername;
            this.cardnumber = cardnumber;
            this.mobile = mobile;
        }

        public String getTourername() {
            return tourername;
        }

        public void setTourername(String tourername) {
            this.tourername = tourername;
        }

        public String getCardnumber() {
            return cardnumber;
        }

        public void setCardnumber(String cardnumber) {
            this.cardnumber = cardnumber;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
