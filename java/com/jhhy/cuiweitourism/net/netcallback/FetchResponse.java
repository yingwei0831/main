package com.jhhy.cuiweitourism.net.netcallback;

import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;

/**
 * Created by zhangguang on 16/9/30.
 */
public abstract class FetchResponse {

    public BizCallback bizCallback = null;

    public  FetchResponse(BizCallback callback){
        this.bizCallback = callback;
    }

    public abstract void onCompletion(FetchResponseModel response);

    public abstract void onError(FetchError error);
}
