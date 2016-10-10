package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/10.
 */
public class ForeEndAdvertise extends  BasicFetchModel {

    //"mark":"index"
    public String mark;
    public String index;

    public ForeEndAdvertise(String mark, String index) {
        this.mark = mark;
        this.index = index;
    }

    public ForeEndAdvertise() {
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
