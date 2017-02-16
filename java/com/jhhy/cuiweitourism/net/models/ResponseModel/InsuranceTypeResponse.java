package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceTypeResponse implements Serializable{

    /**
     * id : 1
     * companykey : D
     * productcode : D0001
     * productname : 10元套餐
     * defaultprice : 10
     * ourprice : ["5000","15000","120000","120000"]
     */

    private String id;
    private String companykey;
    private String productcode;
    private String productname;
    private String defaultprice;
    private List<String> ourprice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanykey() {
        return companykey;
    }

    public void setCompanykey(String companykey) {
        this.companykey = companykey;
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getDefaultprice() {
        return defaultprice;
    }

    public void setDefaultprice(String defaultprice) {
        this.defaultprice = defaultprice;
    }

    public List<String> getOurprice() {
        return ourprice;
    }

    public void setOurprice(List<String> ourprice) {
        this.ourprice = ourprice;
    }

    @Override
    public String toString() {
        return "InsuranceTypeResponse{" +
                "id='" + id + '\'' +
                ", companykey='" + companykey + '\'' +
                ", productcode='" + productcode + '\'' +
                ", productname='" + productname + '\'' +
                ", defaultprice='" + defaultprice + '\'' +
                ", ourprice=" + ourprice +
                '}';
    }
}
