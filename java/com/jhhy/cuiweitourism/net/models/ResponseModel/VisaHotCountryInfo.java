package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by zhangguang on 16/10/9.
 */
public class VisaHotCountryInfo  {

    //{"litpic":"http:\/\/www.cwly1118.com\/uploads\/main\/allimg\/20150925\/20150925132538.jpg","kindname":"新加坡","id":"4"}
    public String litpic;
    public String kindname;
    public String id;

    public VisaHotCountryInfo(String litpic, String kindname, String id) {
        this.litpic = litpic;
        this.kindname = kindname;
        this.id = id;
    }

    public VisaHotCountryInfo() {
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic;
    }

    public String getKindname() {
        return kindname;
    }

    public void setKindname(String kindname) {
        this.kindname = kindname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "VisaHotCountryInfo{" +
                "litpic='" + litpic + '\'' +
                ", kindname='" + kindname + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
