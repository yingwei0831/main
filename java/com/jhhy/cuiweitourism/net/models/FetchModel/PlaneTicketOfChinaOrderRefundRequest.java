package com.jhhy.cuiweitourism.net.models.FetchModel;

import java.util.List;

/**
 * Created by jiahe008 on 2017/1/6.
 * 国内机票申请退款
 */
public class PlaneTicketOfChinaOrderRefundRequest extends BasicFetchModel {

    /**
     * ordersn : 82900924239325
     * memberid : 52
     * tuipiaoren : ["秦绍名"]
     * ticketNo : ["595847545"]
     * segment : PEK-SHA
     */

    private String ordersn;
    private String memberid;
    private String segment;
    private List<String> tuipiaoren;
    private List<String> ticketNo;

    public PlaneTicketOfChinaOrderRefundRequest(String ordersn, String memberid, String segment) {
        this.ordersn = ordersn;
        this.memberid = memberid;
        this.segment = segment;
    }

    public String getOrdersn() {
        return ordersn;
    }

    public void setOrdersn(String ordersn) {
        this.ordersn = ordersn;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getSegment() {
        return segment;
    }

    public void setSegment(String segment) {
        this.segment = segment;
    }

    public List<String> getTuipiaoren() {
        return tuipiaoren;
    }

    public void setTuipiaoren(List<String> tuipiaoren) {
        this.tuipiaoren = tuipiaoren;
    }

    public List<String> getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(List<String> ticketNo) {
        this.ticketNo = ticketNo;
    }

    @Override
    public String toString() {
        return "PlaneTicketOfChinaOrderRefundRequest{" +
                "ordersn='" + ordersn + '\'' +
                ", memberid='" + memberid + '\'' +
                ", segment='" + segment + '\'' +
                ", tuipiaoren=" + tuipiaoren +
                ", ticketNo=" + ticketNo +
                '}';
    }
}
