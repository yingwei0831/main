package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/12.
 */
public class ForeEndMoreCommentFetch extends BasicFetchModel {

    //:{"typeid":"1","articleid":"2"}

    public String typeid;
    public String articleid;

    public ForeEndMoreCommentFetch(String typeid, String articleid) {
        this.typeid = typeid;
        this.articleid = articleid;
    }

    public ForeEndMoreCommentFetch() {
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public String getArticleid() {
        return articleid;
    }

    public void setArticleid(String articleid) {
        this.articleid = articleid;
    }
}
