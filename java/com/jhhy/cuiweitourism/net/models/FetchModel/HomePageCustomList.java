package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by birney1 on 16/10/9.
 */
public class HomePageCustomList extends  BasicFetchModel {

    //{"page":"1","offset":"10"}
    public String page;
    public String offset;

    public HomePageCustomList(String page, String offset) {
        this.page = page;
        this.offset = offset;
    }

    public HomePageCustomList() {
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }
}
