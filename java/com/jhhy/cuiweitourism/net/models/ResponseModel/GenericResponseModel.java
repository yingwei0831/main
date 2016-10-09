package com.jhhy.cuiweitourism.net.models.ResponseModel;

/**
 * Created by birney1 on 16/10/9.
 */
public class GenericResponseModel<T> {
    public FetchResponseModel.HeadModel headModel;
    public T body;

    public GenericResponseModel(FetchResponseModel.HeadModel headModel, T body) {
        this.headModel = headModel;
        this.body = body;
    }
}
