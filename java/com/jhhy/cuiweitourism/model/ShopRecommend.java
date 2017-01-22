package com.jhhy.cuiweitourism.model;

/**
 * Created by birney1 on 16/9/29.
 * 找商铺——>商铺
 */
public class ShopRecommend {

//    {
//        "sid": "14",
//        "suppliername": "易途假期",
//        "logo": "http://www.cwly1118.com/uploads/member/584138eb8c7c2.png"
//    }

    private String sid;
    private String suppliername;
    private String logo;

    public ShopRecommend() {
    }

    public ShopRecommend(String sid, String suppliername, String logo) {
        this.sid = sid;
        this.suppliername = suppliername;
        this.logo = logo;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSuppliername() {
        return suppliername;
    }

    public void setSuppliername(String suppliername) {
        this.suppliername = suppliername;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
