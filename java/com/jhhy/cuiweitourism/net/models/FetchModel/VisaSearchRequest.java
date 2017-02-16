package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008_lvlanlan on 2017/2/16.
 */
public class VisaSearchRequest extends BasicFetchModel {

    /**
     * keys : 韩国
     */

    private String keys;

    public VisaSearchRequest(String keys) {
        this.keys = keys;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }
}
