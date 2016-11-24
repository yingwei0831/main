package com.jhhy.cuiweitourism.net.models.ResponseModel;

import java.io.Serializable;

/**
 * Created by jiahe008 on 2016/11/24.
 */
public class CuiweiInfoResponse implements Serializable{

//    "id": "1",
//    "servername": "关于我们",
//    "title": "关于我们",
//    "url": "http://www.cwly1118.com/service.php?m=test&amp;a=show&amp;id=1"

    private String id;
    private String servername;
    private String title;
    private String url;

    public CuiweiInfoResponse() {
    }

    public CuiweiInfoResponse(String id, String servername, String title, String url) {
        this.id = id;
        this.servername = servername;
        this.title = title;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServername() {
        return servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "CuiweiInfoResponse{" +
                "id='" + id + '\'' +
                ", servername='" + servername + '\'' +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
