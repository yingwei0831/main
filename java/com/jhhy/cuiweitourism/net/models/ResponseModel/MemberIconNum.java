package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by jiahe008 on 2016/11/23.
 */
public class MemberIconNum  {
    public String lvb;

    public MemberIconNum() {
    }

    public MemberIconNum(String lvb) {
        this.lvb = lvb;
    }

    public String getLvb() {
        return lvb;
    }

    public void setLvb(String lvb) {
        this.lvb = lvb;
    }

    @Override
    public String toString() {
        return "MemberIconNum{" +
                "lvb='" + lvb + '\'' +
                '}';
    }
}
