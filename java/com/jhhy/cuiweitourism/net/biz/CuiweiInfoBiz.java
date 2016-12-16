package com.jhhy.cuiweitourism.net.biz;

import com.google.gson.internal.ObjectConstructor;
import com.jhhy.cuiweitourism.net.models.FetchModel.NullArrayFetchModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.CuiweiInfoResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.netcallback.BizCallback;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by jiahe008 on 2016/11/24.
 */
public class CuiweiInfoBiz extends BasicActionBiz{

    /**
     * 请求关于翠微相关网址
     * @param callBack
     */
    public void fetchCuiweiInfo(BizGenericCallback<ArrayList<CuiweiInfoResponse>> callBack){
        NullArrayFetchModel fetchModel = new NullArrayFetchModel();
        fetchModel.code = "Publics_service";
        FetchGenericResponse<ArrayList<CuiweiInfoResponse>> fetchResponse = new FetchGenericResponse<ArrayList<CuiweiInfoResponse>>(callBack) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<CuiweiInfoResponse> array = parseJsonToObjectArray(response, CuiweiInfoResponse.class);
                GenericResponseModel<ArrayList<CuiweiInfoResponse>> returnModel = new GenericResponseModel<>(response.head, array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.onError(error);
            }
        };
        HttpUtils.executeXutils(fetchModel, new FetchGenericCallback<>(fetchResponse));
    }
}
