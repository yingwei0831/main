package com.jhhy.cuiweitourism.net.netcallback;

import com.jhhy.cuiweitourism.net.models.ResponseModel.BasicResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;

/**
 * Created by birney1 on 16/10/9.
 */
public abstract class BizGenericCallback<T> {

    public abstract void onCompletion(GenericResponseModel<T> model);
    public abstract void onError(FetchError error);

}
