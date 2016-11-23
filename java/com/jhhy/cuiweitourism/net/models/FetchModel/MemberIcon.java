package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/11/23.
 */
public class MemberIcon extends BasicFetchModel {

    public String mid;

    public MemberIcon() {
    }

    public MemberIcon(String mid) {
        this.mid = mid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    @Override
    public String toString() {
        return "MemberIcon{" +
                "mid='" + mid + '\'' +
                '}';
    }

}
