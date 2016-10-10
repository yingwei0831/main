package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class MemberCenterMsg extends BasicFetchModel {

    public String mid;

    public MemberCenterMsg(String mid) {
        this.mid = mid;
    }

    public MemberCenterMsg() {
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
