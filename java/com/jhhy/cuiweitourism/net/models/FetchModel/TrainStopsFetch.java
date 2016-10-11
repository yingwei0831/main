package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by zhangguang on 16/10/11.
 */
public class TrainStopsFetch extends BasicFetchModel {

    public String traincode;

    public TrainStopsFetch(String traincode) {
        this.traincode = traincode;
    }

    public TrainStopsFetch() {
    }

    public String getTraincode() {
        return traincode;
    }

    public void setTraincode(String traincode) {
        this.traincode = traincode;
    }
}
