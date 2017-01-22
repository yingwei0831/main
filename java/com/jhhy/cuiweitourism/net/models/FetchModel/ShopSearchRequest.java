package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2017/1/22.
 */
public class ShopSearchRequest extends BasicFetchModel {

    /**
     * keyword : 北京
     */

    private String keyword;

    public ShopSearchRequest(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
