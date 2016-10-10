package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ForeEndSearch extends  BasicFetchModel {

    public String keyword;

    public ForeEndSearch(String keyword) {
        this.keyword = keyword;
    }

    public ForeEndSearch() {
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
