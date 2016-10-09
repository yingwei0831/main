package com.jhhy.cuiweitourism.net.netcallback;

import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;

/**
 * Created by birney1 on 16/10/9.
 */
public abstract class FetchGenericResponse<T> {
    public BizGenericCallback<T> bizCallback = null;

    public  FetchGenericResponse(BizGenericCallback<T> callback){
        this.bizCallback = callback;
    }

    public abstract void onCompletion(FetchResponseModel response);

    public abstract void onError(FetchError error);
}
