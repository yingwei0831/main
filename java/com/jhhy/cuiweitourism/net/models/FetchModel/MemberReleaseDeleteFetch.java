package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/11/23.
 */
public class MemberReleaseDeleteFetch extends BasicFetchModel {

    public String id;

    public MemberReleaseDeleteFetch() {
    }

    public MemberReleaseDeleteFetch(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "MemberReleaseDeleteFetch{" +
                "id='" + id + '\'' +
                '}';
    }
}
