package com.jhhy.cuiweitourism.net.biz;

import com.jhhy.cuiweitourism.net.models.FetchModel.InsuranceOrderRequest;
import com.jhhy.cuiweitourism.net.models.FetchModel.InsuranceRequest;
import com.jhhy.cuiweitourism.net.models.ResponseModel.ActivityHotInfo;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchError;
import com.jhhy.cuiweitourism.net.models.ResponseModel.FetchResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.GenericResponseModel;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceOrderResponse;
import com.jhhy.cuiweitourism.net.models.ResponseModel.InsuranceTypeResponse;
import com.jhhy.cuiweitourism.net.netcallback.BizGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchCallBack;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericCallback;
import com.jhhy.cuiweitourism.net.netcallback.FetchGenericResponse;
import com.jhhy.cuiweitourism.net.netcallback.HttpUtils;

import java.util.ArrayList;

/**
 * Created by jiahe008_lvlanlan on 2017/2/15.
 */
public class InsuranceBiz extends BasicActionBiz{

    public InsuranceBiz() {
    }

    public void getInsuranceType(InsuranceRequest fetch, BizGenericCallback<ArrayList<InsuranceTypeResponse>> callback){
        fetch.code = "Insurance_fangan";
        FetchGenericResponse<ArrayList<InsuranceTypeResponse>> response = new FetchGenericResponse<ArrayList<InsuranceTypeResponse>>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                ArrayList<InsuranceTypeResponse> array = parseJsonToObjectArray(response, InsuranceTypeResponse.class);
                GenericResponseModel returnModel = new GenericResponseModel<>(response.head, array);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(response));
    }

    public void setInsuranceOrder(InsuranceOrderRequest fetch, BizGenericCallback<InsuranceOrderResponse> callback){
        fetch.code = "Insurance_orders";
        FetchGenericResponse<InsuranceOrderResponse> response = new FetchGenericResponse<InsuranceOrderResponse>(callback) {
            @Override
            public void onCompletion(FetchResponseModel response) {
                InsuranceOrderResponse model = parseJsonToObject(response, InsuranceOrderResponse.class);
                GenericResponseModel returnModel = new GenericResponseModel(response.head, model);
                this.bizCallback.onCompletion(returnModel);
            }

            @Override
            public void onError(FetchError error) {
                this.bizCallback.onError(error);
            }
        };
        HttpUtils.executeXutils(fetch, new FetchGenericCallback<>(response));
    }

}
