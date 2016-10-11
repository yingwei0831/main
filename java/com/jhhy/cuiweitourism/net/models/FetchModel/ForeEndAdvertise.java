package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ForeEndAdvertise extends  BasicFetchModel {

    //"mark":"index"
    public String mark;

    public ForeEndAdvertise(String mark) {
        this.mark = mark;
    }

    public ForeEndAdvertise() {
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}
