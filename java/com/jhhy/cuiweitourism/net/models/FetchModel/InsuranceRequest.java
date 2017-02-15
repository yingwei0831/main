package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceRequest extends BasicFetchModel{

    /**
     * areatype : D
     */

    private String areatype;

    public InsuranceRequest(String areatype) {
        this.areatype = areatype;
    }

    public String getAreatype() {
        return areatype;
    }

    public void setAreatype(String areatype) {
        this.areatype = areatype;
    }
}
